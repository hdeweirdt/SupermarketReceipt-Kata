package supermarket.model

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
            val quantity = productQuantity.quantity
            if (offers.containsKey(productQuantity.product)) {
                val offer = offers[productQuantity.product]!!
                val unitPrice = catalog.getUnitPrice(productQuantity.product)
                val quantityAsInt = quantity.toInt()
                var discount: Discount? = null
                var productQuantityPerOfferApplication = 1
                if (offer.offerType === SpecialOfferType.ThreeForTwo) {
                    productQuantityPerOfferApplication = 3

                } else if (offer.offerType === SpecialOfferType.TwoForAmount) {
                    productQuantityPerOfferApplication = 2
                    if (quantityAsInt >= 2) {
                        val total = offer.argument * (quantityAsInt / productQuantityPerOfferApplication) + quantityAsInt % 2 * unitPrice
                        val discountN = unitPrice * quantity - total
                        discount = Discount(productQuantity.product, "2 for " + offer.argument, discountN)
                    }

                }
                if (offer.offerType === SpecialOfferType.FiveForAmount) {
                    productQuantityPerOfferApplication = 5
                }
                val timesToRepeatOffer = quantityAsInt / productQuantityPerOfferApplication
                if (offer.offerType === SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
                    val discountAmount =
                        quantity * unitPrice - (timesToRepeatOffer.toDouble() * 2.0 * unitPrice + quantityAsInt % 3 * unitPrice)
                    discount = Discount(productQuantity.product, "3 for 2", discountAmount)
                }
                if (offer.offerType === SpecialOfferType.TenPercentDiscount) {
                    discount =
                        Discount(
                            productQuantity.product,
                            offer.argument.toString() + "% off",
                            quantity * unitPrice * offer.argument / 100.0
                        )
                }
                if (offer.offerType === SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
                    val discountTotal =
                        unitPrice * quantity - (offer.argument * timesToRepeatOffer + quantityAsInt % 5 * unitPrice)
                    discount = Discount(productQuantity.product, productQuantityPerOfferApplication.toString() + " for " + offer.argument, discountTotal)
                }
                if (discount != null)
                    receipt.addDiscount(discount)
            }

        }
    }
}
