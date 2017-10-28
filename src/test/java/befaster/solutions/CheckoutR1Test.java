package befaster.solutions;


import org.junit.Test;

class CheckoutR1Test {
	private static CheckoutR1 checkout;
	
	@BeforeAll
	public static void setup() throws Exception {
		checkout = new CheckoutR1();
	}

	@Test
	public void emptyListOfProducts() throws Exception {
		Assertions.assertEquals(0, checkout.checkout(""));
	}

	@Test
	public void atLeastOneUnknownSku() throws Exception {
		Assertions.assertEquals(-1, checkout.checkout("X"));
		Assertions.assertEquals(-1, checkout.checkout("a"));
		Assertions.assertEquals(-1, checkout.checkout("-"));
		Assertions.assertEquals(-1, checkout.checkout("ABCa"));
	}
	
	@Test
	public void singleProduct() throws Exception {
		Assertions.assertEquals(50, checkout.checkout("A"));
		Assertions.assertEquals(30, checkout.checkout("B"));
		Assertions.assertEquals(20, checkout.checkout("C"));
		Assertions.assertEquals(15, checkout.checkout("D"));
	}
	
	@Test
	public void allProducts() throws Exception {
		Assertions.assertEquals(50 + 30 + 20 + 15, checkout.checkout("ABCD"));
	}
	
	@Test
	public void specialPriceFor3As() throws Exception {
		Assertions.assertEquals(130, checkout.checkout("AAA"));
	}
	
	@Test
	public void ManyAs() throws Exception {
		Assertions.assertEquals(130 + 50, checkout.checkout("AAAA"));
		Assertions.assertEquals(130 + 50 + 50, checkout.checkout("AAAAA"));
		Assertions.assertEquals(130 + 130, checkout.checkout("AAAAAA"));
		
	}
	
	@Test
	public void specialPriceForB() throws Exception {
		Assertions.assertEquals(45, checkout.checkout("BB"));
		Assertions.assertEquals(45 + 30, checkout.checkout("BBB"));
		Assertions.assertEquals(45 + 45, checkout.checkout("BBBB"));
	}
	
	@Test
	public void specialPriceForAandB() throws Exception {
		Assertions.assertEquals(130 + 45, checkout.checkout("ABABA"));
	}
	
	@Test
	public void dodgyCases() throws Exception {
		Assertions.assertEquals(50 * 2 + 45 + 20 * 2 + 15 * 2, checkout.checkout("ABCDABCD"));
		Assertions.assertEquals(50 * 2 + 45 + 20 * 2 + 15 * 2, checkout.checkout("BABDDCAC"));
		Assertions.assertEquals(130 + 45, checkout.checkout("AAABB"));
		Assertions.assertEquals(130 * 2 + 50 + 45 * 2 + 30 + 20 * 3 + 15, checkout.checkout("ABCDCBAABCABBAAA"));
	}
}
