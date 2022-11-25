package com.ecommerceconcept.app.product_detail

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.toColorInt
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ecommerceconcept.app.R
import com.ecommerceconcept.app.core.BaseFragment
import com.ecommerceconcept.app.utils.launchWith
import com.ecommerceconcept.app.utils.mapChanged
import com.ecommerceconcept.app.utils.onEachChanged
import com.ecommerceconcept.app.databinding.*
import com.ecommerceconcept.app.utils.epicadapter.EpicAdapter
import com.ecommerceconcept.app.utils.epicadapter.EpicAdapterBuilder
import com.ecommerceconcept.app.utils.epicadapter.bind
import com.ecommerceconcept.app.utils.epicadapter.requireEpicAdapter
import com.ecommerceconcept.app.utils.safeActionNavigate
import com.ecommerceconcept.app.utils.toMemoryFormat
import com.ecommerceconcept.app.utils.toPriceFormat
import com.ecommerceconcept.app.utils.setShowSideItems
import com.ecommerceconcept.product_detail.presentation.ProductDetailViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetailFragment : BaseFragment<ProductDetailBinding>(ProductDetailBinding::inflate) {

    private val viewModel: ProductDetailViewModel by viewModel() {
        parametersOf(
            ProductDetailFragmentArgs.fromBundle(
                requireArguments()
            ).productId
        )
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        with(binding) {

            colorsRecyclerview.adapter = buildColorsAdapter()
            capacityRecyclerview.adapter = buildCapacityAdapter()

            mainRecyclerview.adapter = buildMainAdapter().apply {
                loadItems(
                    listOf(
                        ProductDetailViewType.Slider,
                        ProductDetailViewType.Details
                    )
                )
            }

            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            addToCartButton.setOnClickListener {
                viewModel.addToCart()
            }

            showCartButton.setOnClickListener {
                findNavController().safeActionNavigate(ProductDetailFragmentDirections.actionProductDetailToCart())
            }

            binding.includedExceptionDialog.retryButton.setOnClickListener {
                viewModel.load()
            }

            viewModel.state.filterIsInstance<ProductDetailViewModel.State.LoadingError>().onEachChanged {
                binding.includedExceptionDialog.errorText.text =
                    it.error?.toString(requireContext()) ?: "Ошибка"
            }.launchWith(viewLifecycleOwner)

            viewModel.state.onEachChanged {
                binding.includedProgressbar.progressBarLayout.isVisible = it is ProductDetailViewModel.State.Loading
                binding.includedExceptionDialog.exceptionDialog.isVisible =
                    it is ProductDetailViewModel.State.LoadingError
            }.launchWith(viewLifecycleOwner)

            viewModel.state.filterIsInstance<ProductDetailViewModel.State.Loaded>().map {
                it.productDetail.color
            }.onEachChanged {
                colorsRecyclerview.requireEpicAdapter().loadItems(it)
            }.launchWith(viewLifecycleOwner)

            viewModel.state.filterIsInstance<ProductDetailViewModel.State.Loaded>().map {
                it.productDetail.capacity
            }.onEachChanged {
                capacityRecyclerview.requireEpicAdapter().loadItems(it)
            }.launchWith(viewLifecycleOwner)

            viewModel.state.filterIsInstance<ProductDetailViewModel.State.Loaded>().map {
                it.productDetail.price
            }.onEachChanged {
                priceTextview.text = it.toPriceFormat()
            }.launchWith(viewLifecycleOwner)

        }
    }

    private fun buildMainAdapter() = EpicAdapter {
        imagePagerSetup()
        detailsSetup()
    }

    private fun EpicAdapterBuilder.detailsSetup() =
        setup<ProductDetailViewType.Details, DetailsViewtypeBinding>(DetailsViewtypeBinding::inflate) {

            init {
                detailsViewpager.adapter = buildDetailsViewPagerAdapter()
            }

            bind { scope, _, _ ->

                TabLayoutMediator(detailsTablayout, detailsViewpager) { tab, position ->
                    when (position) {
                        0 -> tab.text = getString(R.string.shop)
                        1 -> tab.text = getString(R.string.details)
                        2 -> tab.text = getString(R.string.features)
                    }
                }.attach()

                detailsViewpager.requireEpicAdapter().loadItems(
                    listOf(
                        DetailPagerlViewType.Shop,
                        DetailPagerlViewType.Details,
                        DetailPagerlViewType.Features
                    )
                )

                combine(
                    viewModel.state.filterIsInstance<ProductDetailViewModel.State.Loaded>()
                        .mapChanged { it.productDetail.title },
                    viewModel.state.filterIsInstance<ProductDetailViewModel.State.Loaded>()
                        .mapChanged { it.productDetail.rating }
                ) { title, rating ->
                    productNameTextview.text = title
                    ratingBar.rating = rating
                }.launchIn(scope)

                favouriteImageview.setOnClickListener {
                    viewModel.toggleFavourite()
                    val icon = if (viewModel.isFavourite()) R.drawable.filled_favourites_main_color_icon else R.drawable.favourites_icon_14
                    favouriteImageview.setImageResource(icon)
                }
            }
        }

    private data class ShopPageContent(
        val cpu: String,
        val camera: String,
        val sd: String,
        val ssd: String
    )

    private fun buildDetailsViewPagerAdapter() = EpicAdapter {
        setup<DetailPagerlViewType.Shop, ViewpagerShopPageBinding>(ViewpagerShopPageBinding::inflate) {
            bind { scope, _, _ ->

                viewModel.state.filterIsInstance<ProductDetailViewModel.State.Loaded>().map {
                    ShopPageContent(
                        it.productDetail.cpu,
                        it.productDetail.camera,
                        it.productDetail.sd,
                        it.productDetail.ssd,
                    )
                }.onEachChanged {
                    cpuTextview.text = it.cpu
                    ramTextview.text = it.ssd
                    cameraTextview.text = it.camera
                    sdCardTextview.text = it.sd
                }.launchIn(scope)
            }
        }
        setup<DetailPagerlViewType.Details, ViewpagerDetailsPageBinding>(ViewpagerDetailsPageBinding::inflate)
        { bind { _ -> } }
        setup<DetailPagerlViewType.Features, ViewpagerFeaturesPageBinding>(ViewpagerFeaturesPageBinding::inflate)
        { bind { _ -> } }
    }


    private fun EpicAdapterBuilder.imagePagerSetup() =
        setup<ProductDetailViewType.Slider, SliderViewtypeBinding>(SliderViewtypeBinding::inflate) {
            init {
                imageRecyclerview.adapter = buildImagesAdapter()
                imageRecyclerview.setShowSideItems()
            }

            bind { scope, _, _ ->
                viewModel.state.filterIsInstance<ProductDetailViewModel.State.Loaded>().map {
                    it.productDetail.images
                }.onEachChanged {
                    imageRecyclerview.requireEpicAdapter().loadItems(it)
                }.launchIn(scope)
            }
        }

    private fun buildImagesAdapter() = EpicAdapter {
        setup<String, SliderItemBinding>(SliderItemBinding::inflate) {
            bind { item ->
                Glide.with(requireContext()).load(item).into(productImageview)
            }
        }
    }


    private fun buildColorsAdapter() = EpicAdapter {
        setup<String, ColorItemBinding>(ColorItemBinding::inflate) {
            bind { scope, _, item ->
                colorItem.backgroundTintList = ColorStateList.valueOf(item.toColorInt())

                viewModel.state.filterIsInstance<ProductDetailViewModel.State.Loaded>().map {
                    it.selectedColor == item
                }.onEachChanged {
                    val color =
                        if (it) AppCompatResources.getDrawable(requireContext(), R.drawable.color_check_icon) else null
                    colorItem.setImageDrawable(color)
                }.launchIn(scope)

                root.setOnClickListener {
                    viewModel.updateColor(item)
                }
            }
        }
    }

    private fun buildCapacityAdapter() = EpicAdapter {
        setup<String, CapacityItemBinding>(CapacityItemBinding::inflate) {
            bind { scope, _, item ->
                capacityItem.text = item.toMemoryFormat()

                viewModel.state.filterIsInstance<ProductDetailViewModel.State.Loaded>().map {
                    it.selectedCapacity == item
                }.onEachChanged {
                    if (it) {
                        capacityItem.setTextColor(requireContext().getColor(R.color.white))
                        capacityItem.backgroundTintList = requireContext().getColorStateList(R.color.main_color)
                    } else {
                        capacityItem.setTextColor(requireContext().getColor(R.color.capacity_unchecked))
                        capacityItem.backgroundTintList = requireContext().getColorStateList(R.color.background_color)
                    }
                }.launchIn(scope)

                root.setOnClickListener {
                    viewModel.updateCapacity(item)
                }
            }
        }
    }

    private sealed class ProductDetailViewType {
        object Slider : ProductDetailViewType()
        object Details : ProductDetailViewType()
    }

    private sealed class DetailPagerlViewType {
        object Shop : DetailPagerlViewType()
        object Details : DetailPagerlViewType()
        object Features : DetailPagerlViewType()
    }
}