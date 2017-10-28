package befaster.solutions;

import java.util.ArrayList;
import java.util.List;

public class Checkout {
    public static Integer checkout(String skus) {
        Checkout checkout = new Checkout();
        List<String> products = checkout.convertToList(skus.toCharArray());

        return checkout.calculateTotalPrice(products);
    }

    private int calculateTotalPrice(List<String> skus) {
        int totalPrice = 0;

        while(countSku(skus, "F") >= 3) {
            if(countSku(skus, "F") >= 3) {
//				long howManyFs = countSku(skus, "F");
//				long numberOfPairs = howManyFs % 2;
//				for(int i = 0; i < numberOfPairs; i++) {
//					skus.remove("F");
//				}

                totalPrice += getPriceOfSingleProduct("F") * 2;
                skus.remove("F");
                skus.remove("F");
                skus.remove("F");
            }
        }

        while(countSku(skus, "E") >= 2) {
            if(countSku(skus, "E") >= 2) {
                totalPrice += getPriceOfSingleProduct("E") * 2;
                skus.remove("E");
                skus.remove("E");
                skus.remove("B");
            }
        }

        while(countSku(skus, "A") >= 3) {
            if(countSku(skus, "A") >= 5) {
                totalPrice += 200;
                skus.remove("A");
                skus.remove("A");
                skus.remove("A");
                skus.remove("A");
                skus.remove("A");
            } else {
                totalPrice += 130;
                skus.remove("A");
                skus.remove("A");
                skus.remove("A");
            }
        }

        while(countSku(skus, "B") >= 2) {
            if(countSku(skus, "B") >= 2) {
                totalPrice += 45;
                skus.remove("B");
                skus.remove("B");
            }
        }

        try {
            for(String product: skus) {
                Integer x = getPriceOfSingleProduct(product);
                totalPrice += x;
            }
        } catch (IllegalSkuException e) {
            return -1;
        }
        return totalPrice;
    }

    private long countSku(List<String> skus, String a) {
        return skus.stream().filter(s -> s.equals(a)).count();
    }

    private List<String> convertToList(char[] chars) {
        List<String> skus = new ArrayList<>();
        for(char c: chars) {
            skus.add(String.valueOf(c));
        }
        return skus;
    }

    private Integer getPriceOfSingleProduct(String sku) {
        if(sku.equals("A")) {
            return 50;
        }
        if(sku.equals("B")) {
            return 30;
        }
        if(sku.equals("C")) {
            return 20;
        }
        if(sku.equals("D")) {
            return 15;
        }
        if(sku.equals("E")) {
            return 40;
        }
        if(sku.equals("F")) {
            return 10;
        }
        throw new IllegalSkuException(sku);
    }

    private static class IllegalSkuException extends RuntimeException {
        private String sku;

        public IllegalSkuException(String sku) {
            this.sku = sku;
        }

        @Override
        public String toString() {
            return "IllegalSkuException{" +
                    "sku'" + sku + '\'' +
                    '}';
        }
    }
}
