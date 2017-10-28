package befaster.solutions;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CheckoutTest {

	@Test
	public void emptyListOfProducts() throws Exception {
		assertEquals(0, checkout(""));
	}

    @Test
	public void atLeastOneUnknownSku() throws Exception {
		assertEquals(-1, checkout("X"));
		assertEquals(-1, checkout("a"));
		assertEquals(-1, checkout("-"));
		assertEquals(-1, checkout("ABCa"));
	}

	@Test
	public void singleProduct() throws Exception {
		assertEquals(50, checkout("A"));
		assertEquals(30, checkout("B"));
		assertEquals(20, checkout("C"));
		assertEquals(15, checkout("D"));
	}

	@Test
	public void allProducts() throws Exception {
		assertEquals(50 + 30 + 20 + 15, checkout("ABCD"));
	}

	@Test
	public void specialPriceFor3As() throws Exception {
		assertEquals(130, checkout("AAA"));
	}

	@Test
	public void ManyAs() throws Exception {
		assertEquals(130 + 50, checkout("AAAA"));
		assertEquals(130 + 50 + 50, checkout("AAAAA"));
		assertEquals(130 + 130, checkout("AAAAAA"));

	}

	@Test
	public void specialPriceForB() throws Exception {
		assertEquals(45, checkout("BB"));
		assertEquals(45 + 30, checkout("BBB"));
		assertEquals(45 + 45, checkout("BBBB"));
	}

	@Test
	public void specialPriceForAandB() throws Exception {
		assertEquals(130 + 45, checkout("ABABA"));
	}

	@Test
	public void dodgyCases() throws Exception {
		assertEquals(50 * 2 + 45 + 20 * 2 + 15 * 2, checkout("ABCDABCD"));
		assertEquals(50 * 2 + 45 + 20 * 2 + 15 * 2, checkout("BABDDCAC"));
		assertEquals(130 + 45, checkout("AAABB"));
		assertEquals(130 * 2 + 50 + 45 * 2 + 30 + 20 * 3 + 15, checkout("ABCDCBAABCABBAAA"));
	}

	//~~~ Helpers

    private static int checkout(String skus) {
        return Checkout.checkout(skus);
    }
}
