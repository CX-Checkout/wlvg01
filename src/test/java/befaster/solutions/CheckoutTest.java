package befaster.solutions;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

	@Test
	public void groupScheme() throws Exception {
		assertEquals(20, checkout("S"));
		assertEquals(20, checkout("T"));
		assertEquals(17, checkout("X"));
		assertEquals(20, checkout("Y"));
		assertEquals(21, checkout("Z"));

		assertEquals(45, checkout("SSS"));
		assertEquals(45, checkout("TTT"));
		assertEquals(45, checkout("XXX"));
		assertEquals(45, checkout("YYY"));
		assertEquals(45, checkout("ZZZ"));

		assertEquals(45*2, checkout("SSSTTT"));
		assertEquals(45*2, checkout("STXYZZ"));
		assertEquals(45*2, checkout("XZYYTS"));
		assertEquals(45*5, checkout("SSSTTTXXXYYYZZZ"));
		assertEquals(45 + 20 + 17, checkout("STXYZ"));
	}

	@Test
	public void groupIsNotCombinedWithOtherOffers() throws Exception {
		assertEquals(45 + 50, checkout("SSSA"));
//		assertEquals(20*3 + 130, checkout("SSSAAA"), "discount on group is 15, discount on A is 20");
	}

	//~~~ Helpers

    private static int checkout(String skus) {
        return Checkout.checkout(skus);
    }
}
