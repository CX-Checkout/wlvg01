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
		assertEquals(-1, checkout("l"));
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
		assertEquals(40, checkout("E"));
	}

	@Test
	public void allProducts() throws Exception {
		assertEquals(50 + 30 + 20 + 15, checkout("ABCD"));
	}

	@Test
	public void differentThresholdsScheme() throws Exception {
		assertEquals(130, checkout("AAA"));
		assertEquals(200, checkout("AAAAA"));
		assertEquals(130 + 50, checkout("AAAA"));
		assertEquals(200, checkout("AAAAA"));
		assertEquals(200 + 50, checkout("AAAAAA"));
		assertEquals(200 + 130, checkout("AAAAAAAA"));
		assertEquals(200 + 130 + 50, checkout("AAAAAAAAA"));
		assertEquals(200 + 200, checkout("AAAAAAAAAA"));

		assertEquals(10, checkout("H"));
		assertEquals(10*4, checkout("HHHH"));
		assertEquals(45, checkout("HHHHH")); // 5
		assertEquals(80, checkout("HHHHHHHHHH")); // 10
		assertEquals(80 + 45, checkout("HHHHHHHHHHHHHHH")); // 15
	}

	@Test
	public void singleThresholdScheme() throws Exception {
		assertEquals(45, checkout("BB"));
		assertEquals(45 + 30, checkout("BBB"));
		assertEquals(45 + 45, checkout("BBBB"));

		assertEquals(200, checkout("PPPPP"));
		assertEquals(200*2, checkout("PPPPPPPPPP"));
	}

	@Test
	public void specialPriceForAandB() throws Exception {
		assertEquals(130 + 45, checkout("ABABA"));
	}

	@Test
	public void dodgyCases() throws Exception {
		assertEquals(50*2 + 45 + 20*2 + 15*2, checkout("ABCDABCD"));
		assertEquals(50*2 + 45 + 20*2 + 15*2, checkout("BABDDCAC"));
		assertEquals(130 + 45, checkout("AAABB"));
		assertEquals(200 + 50*2 + 45*2 + 30 + 20*3 + 15, checkout("ABCDCBAABCABBAAA"));
	}

	@Test
	public void xProductsAndOneOtherFree() throws Exception {
		assertEquals(80, checkout("EEB"));
		assertEquals(120, checkout("EEEB"));
		assertEquals(160, checkout("EEEEBB"));
		assertEquals(160 + 30, checkout("EEEEBBB"));

		assertEquals(120, checkout("NNNM"));
	}

	@Test
	public void specailDealForJustE() throws Exception {
		assertEquals(80, checkout("EE"));
	}

	@Test
	public void xProductsAndSameOneFree() throws Exception {
		assertEquals(10*2, checkout("FF"));
		assertEquals(10*2, checkout("FFF"));
		assertEquals(10*3, checkout("FFFF"));
		assertEquals(10*4, checkout("FFFFF"));
		assertEquals(10*4, checkout("FFFFFF"));

		assertEquals(40*3, checkout("UUU"));
	}

	//~~~ Helpers

    private static int checkout(String skus) {
        return Checkout.checkout(skus);
    }
}
