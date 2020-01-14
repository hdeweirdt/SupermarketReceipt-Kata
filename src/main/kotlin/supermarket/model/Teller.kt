package supermarket.model

import supermarket.model.offer.Offer
import supermarket.model.offer.SpecialOfferType
import supermarket.model.offer.ThreeForTwo
import java.util.*


class Teller(private val catalog: SupermarketCatalog) {
    private val offers = HashMap<Product, Offer>()

    fun addSpecialOffer(offerType: SpecialOfferType, product: Product, argument: Double) {
        if (offerType == SpecialOfferType.ThreeForTwo) {
            this.offers[product] = ThreeForTwo(product, argument)
        } else {
            this.offers[product] = Offer(offerType, product, argument)
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
