package supermarket.model

import supermarket.model.offer.*
import java.util.*


class Teller(private val catalog: SupermarketCatalog) {
    private val offers = HashMap<Product, Offer>()

    fun addSpecialOffer(offerType: SpecialOfferType, product: Product, argument: Double) {
        this.offers[product] = when (offerType) {
            SpecialOfferType.TwoForAmount -> TwoForAmount(product, argument)
            SpecialOfferType.ThreeForTwo -> ThreeForTwo(product, argument)
            SpecialOfferType.TenPercentDiscount -> TenPercent(product, argument)
            SpecialOfferType.FiveForAmount -> FiveForAmount(product, argument)
        }
    }

    fun checksOutArticlesFrom(theCart: ShoppingCart): Receipt {
        val receipt = Receipt()
        val productQuantities = theCart.getItems()
        for (pq in productQuantities) {
            val p = pq.product
            val quantity = pq.quantity
            val unitPrice = this.catalog.getUnitPrice(p)
            val price = quantity * unitPrice
            receipt.addProduct(p, quantity, unitPrice, price)
        }
        theCart.handleOffers(receipt, this.offers, this.catalog)

        return receipt
    }

}
