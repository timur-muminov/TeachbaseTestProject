package com.ecommerceconcept.app.home

import android.graphics.Paint
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.ecommerceconcept.app.R
import com.ecommerceconcept.app.core.BaseFragment
import com.ecommerceconcept.app.databinding.*
import com.ecommerceconcept.app.utils.*
import com.ecommerceconcept.app.utils.epicadapter.EpicAdapter
import com.ecommerceconcept.app.utils.epicadapter.EpicAdapterBuilder
import com.ecommerceconcept.app.utils.epicadapter.requireEpicAdapter
import com.ecommerceconcept.categories.Category
import com.ecommerceconcept.filters.Brand
import com.ecommerceconcept.filters.Price
import com.ecommerceconcept.filters.Size
import com.ecommerceconcept.home.presentation.HomeViewModel
import com.ecommerceconcept.products.BestSellerProduct
import com.ecommerceconcept.products.HotSalesProduct
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

    private val viewModel: HomeViewModel by viewModel()

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        val dialog = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        setBottomSheetDialog(dialog)


        with(binding) {
            filterIcon.setOnClickListener {
                dialog.show()
            }

            cartIcon.setOnClickListener {
                findNavController().safeActionNavigate(HomeFragmentDirections.actionHomeToCart())
            }

            includedExceptionDialog.retryButton.setOnClickListener {
                viewModel.load()
            }

            mainRecyclerview.adapter = getMainAdapter().apply {
                loadItems(
                    listOf(
                        ViewType.Categories,
                        ViewType.HotSales,
                        ViewType.BestSellers
                    )
                )
            }

            viewModel.state.filterIsInstance<HomeViewModel.State.Loaded>().map {
                it.cartProductsAmount
            }.onEachChanged {
                if (it == 0) productsInCartAmountTextview.isVisible = false else {
                    productsInCartAmountTextview.isVisible = true
                    productsInCartAmountTextview.text = it.toString()
                }
            }.launchWith(viewLifecycleOwner)

            viewModel.state.filterIsInstance<HomeViewModel.State.LoadingError>()
                .onEachChanged {
                    includedExceptionDialog.errorText.text =
                        it.error?.toString(requireContext()) ?: "Ошибка"
                }.launchWith(viewLifecycleOwner)

            viewModel.state.onEachChanged {
                requireActivity().window.statusBarColor =
                    if (it is HomeViewModel.State.Loading) requireContext().getColor(R.color.second_color) else requireContext().getColor(
                        R.color.background_color
                    )
                includedSplashScreen.splashScreen.isVisible = it is HomeViewModel.State.Loading
                includedExceptionDialog.exceptionDialog.isVisible = it is HomeViewModel.State.LoadingError
            }.launchWith(viewLifecycleOwner)

            viewModel.state.filterIsInstance<HomeViewModel.State.Loaded>().map {
                it.location
            }.onEachChanged {
                geoTextView.text = it
            }.launchWith(viewLifecycleOwner)
        }
    }


    private fun getMainAdapter() = EpicAdapter {
        categoriesSetup()
        hotSalesSetup()
        bestSellersSetup()
    }

    private fun EpicAdapterBuilder.categoriesSetup() =
        setup<ViewType.Categories, CategoriesViewtypeBinding>(CategoriesViewtypeBinding::inflate) {
            init {
                categoriesRecyclerview.adapter = getCategoriesAdapter()
            }

            bind { scope, _, _ ->
                viewModel.state.filterIsInstance<HomeViewModel.State.Loaded>().map {
                    it.categories
                }.onEachChanged {
                    categoriesRecyclerview.requireEpicAdapter().loadItems(it)
                }.launchIn(scope)

                viewAllTextView.setOnClickListener {
                    showOnSnackBar("categories view all")
                }

                appsButton.setOnClickListener {
                    showOnSnackBar("apps button")
                }
            }
        }

    private fun EpicAdapterBuilder.hotSalesSetup() =
        setup<ViewType.HotSales, HotSalesViewtypeBinding>(HotSalesViewtypeBinding::inflate) {

            init {
                hotSalesViewpager.adapter = getHotSalesAdapter()
                hotSalesViewpager.setPageTransformer(getPageTransformer(0.88f))
                //viewpager.setInfiniteAnimation(viewLifecycleOwner)
            }

            bind { scope, _, _ ->
                viewModel.state.filterIsInstance<HomeViewModel.State.Loaded>().map {
                    it.products.hotSaleProducts
                }.onEachChanged {
                    hotSalesViewpager.requireEpicAdapter().loadItems(it)
                }.launchIn(scope)


                seeMoreTextView.setOnClickListener {
                    showOnSnackBar("Hot sales see more")
                }
            }
        }

    private fun EpicAdapterBuilder.bestSellersSetup() =
        setup<ViewType.BestSellers, BestSellerViewtypeBinding>(BestSellerViewtypeBinding::inflate) {

            init {
                bestSellerRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
                bestSellerRecyclerview.adapter = getBestSellersAdapter()
            }

            bind { scope, _, _ ->

                viewModel.state.filterIsInstance<HomeViewModel.State.Loaded>().map {
                    it.products.bestSellerProducts
                }.onEachChanged {
                    bestSellerRecyclerview.requireEpicAdapter().loadItems(it)
                }.launchIn(scope)

                seeMoreTextView.setOnClickListener {
                    showOnSnackBar("Best seller see more")
                }
            }
        }


    private fun getCategoriesAdapter() = EpicAdapter {
        setup<Category, CategoryItemBinding>(CategoryItemBinding::inflate) {

            bind { scope, _, item ->
                Glide.with(requireContext()).load(item.picture).into(categoryImage)
                categoryTitle.text = item.title

                viewModel.state.filterIsInstance<HomeViewModel.State.Loaded>().map {
                    it.selectedCategory == item
                }.onEachChanged {
                    setSelection(it, this)
                }.launchIn(scope)

                root.setOnClickListener {
                    viewModel.updateCategory(item)
                    viewModel.refreshProducts()
                }
            }
        }
    }

    private fun getHotSalesAdapter() = EpicAdapter {
        setup<HotSalesProduct, HotSalesItemBinding>(HotSalesItemBinding::inflate) {

            init {
                root.clipToOutline = true
            }

            bind { _, _, item ->
                newTextview.isVisible = item.isNew ?: false
                productNameTextview.text = item.title
                productDescriptionTextview.text = item.subtitle
                buyNowButton.isVisible = item.isBuy

                Glide.with(requireContext()).load(item.picture).into(productPicture)

                root.setOnClickListener {
                    //navigate(item.id)
                }

                buyNowButton.setOnClickListener {
                    showOnSnackBar("Buy now!")
                }
            }
        }
    }

    private fun getBestSellersAdapter() = EpicAdapter {
        setup<BestSellerProduct, BestSellerItemBinding>(BestSellerItemBinding::inflate) {

            init {
                root.clipToOutline = true
                priceWithoutDiscount.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }

            bind { _, _, item ->
                Glide.with(requireContext()).load(item.picture).into(productPicture)
                priceWithDiscount.text = item.discountPrice.toPriceFormat()

                priceWithoutDiscount.text = item.priceWithoutDiscount.toPriceFormat()
                productNameTextview.text = item.title

                favourite.setOnClickListener {
                    viewModel.toggleFavourite(item)
                    val icon =
                        if (viewModel.isFavourite(item)) R.drawable.filled_favourites_main_color_icon else R.drawable.empty_favourites_main_color_icon
                    favourite.setImageResource(icon)
                }

                root.setOnClickListener {
                    navigate(item.id)
                }
            }
        }
    }

    private fun setSelection(selection: Boolean, categoryItemBinding: CategoryItemBinding) {
        if (selection) {
            categoryItemBinding.categoryImage.imageTintList = requireContext().getColorStateList(R.color.white)
            categoryItemBinding.categoryImageContainer.backgroundTintList =
                requireContext().getColorStateList(R.color.main_color)
            categoryItemBinding.categoryTitle.setTextColor(requireContext().getColor(R.color.main_color))
        } else {
            categoryItemBinding.categoryImage.imageTintList = requireContext().getColorStateList(R.color.disabled)
            categoryItemBinding.categoryImageContainer.backgroundTintList =
                requireContext().getColorStateList(R.color.white)
            categoryItemBinding.categoryTitle.setTextColor(requireContext().getColor(R.color.black))
        }
    }

    private sealed class ViewType {
        object Categories : ViewType()
        object HotSales : ViewType()
        object BestSellers : ViewType()
    }


    private fun setBottomSheetDialog(dialog: BottomSheetDialog) {
        val dialogBinding = FilterDialogBinding.inflate(layoutInflater, binding.root, false)

        with(dialogBinding) {
            brandDropDownMenu.setAdapter(getAdapter(R.array.brands_filter))
            priceDropDownMenu.setAdapter(getAdapter(R.array.price_filter))
            sizeDropDownMenu.setAdapter(getAdapter(R.array.size_filter))

            viewModel.state.filterIsInstance<HomeViewModel.State.Loaded>().map {
                it.selectedFilters
            }.onEachChanged { filters ->
                brandDropDownMenu.setText(filters.brand?.title ?: "")
                priceDropDownMenu.setText(filters.price?.range ?: "")
                sizeDropDownMenu.setText(filters.size?.range ?: "")
            }.launchWith(viewLifecycleOwner)

            brandDropDownMenu.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                viewModel.updateBrand(Brand(position, getArray(R.array.brands_filter)[position]))
            }

            priceDropDownMenu.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                viewModel.updatePrice(Price(position, getArray(R.array.price_filter)[position]))
            }

            sizeDropDownMenu.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                viewModel.updateSize(Size(position, getArray(R.array.size_filter)[position]))
            }

            closeDialogButton.setOnClickListener {
                dialog.dismiss()
            }

            submitDialogButton.setOnClickListener {
                viewModel.refreshProducts()
                dialog.dismiss()
            }
            dialog.setContentView(dialogBinding.root)
        }
    }


    private fun navigate(productId: Int) {
        findNavController().safeActionNavigate(
            HomeFragmentDirections.actionHomeToProductDetail(
                productId
            )
        )
    }

    private fun showOnSnackBar(text: String) = Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()

    private fun getAdapter(resource: Int) =
        ArrayAdapter(requireActivity(), R.layout.spinner_item, getArray(resource))

    private fun getArray(resource: Int) = resources.getStringArray(resource)
}
