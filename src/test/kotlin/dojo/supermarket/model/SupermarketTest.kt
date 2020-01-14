package dojo.supermarket.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import supermarket.model.*

class SupermarketTest {
    private val toothbrush = Product("toothbrush", ProductUnit.Each)
    private val apples = Product("apples", ProductUnit.Kilo)

    @Test
    fun shoppingCartWithNoItemsOnOfferIsJustSumOfPrices() {
        val catalog = getFilledCatalog()

        val cart = ShoppingCart()
        cart.addItemQuantity(apples, 2.0)

        val teller = Teller(catalog)
        val receipt = teller.checksOutArticlesFrom(cart)

        assertEquals(3.98, receipt.totalPrice)
    }

    private fun getFilledCatalog(): SupermarketCatalog {
        val catalog = FakeCatalog()
        catalog.addProduct(toothbrush, 0.99)
        catalog.addProduct(apples, 1.99)
        return catalog
    }
}
