package supermarket.model

import supermarket.model.offer.Offer

class ShoppingCart {

    private val items = mutableListOf<ProductQuantity>()

    internal fun getItems(): List<ProductQuantity> {
        return items.toList()
    }

    internal fun addToCart(product: Product) {
        this.addToCart(product, 1.0)
    }

    fun addToCart(product: Product, quantity: Double) {
        items.add(ProductQuantity(product, quantity))
    }

    internal fun handleOffers(receipt: Receipt, offers: Map<Product, Offer>, catalog: SupermarketCatalog) {
        for (productQuantity in items) {
            if (offers.containsKey(productQuantity.product)) {
                val offer = offers[productQuantity.product]!!
                val discount = offer.calculateDiscount(productQuantity, catalog)
                if (discount != null) {
                    receipt.addDiscount(discount)
                }
            }
        }
    }
}
