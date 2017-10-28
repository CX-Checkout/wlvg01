package befaster.solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Checkout {
    public static Integer checkout(String skus) {
        Checkout checkout = new Checkout();
        List<String> products = checkout.convertToList(skus.toCharArray());

        return checkout.calculateTotalPrice(products);
    }
    private int calculateTotalPrice(List<String> skus) {
        int totalPrice = 0;

        while(skus.stream().filter(s -> Arrays.asList("S", "T", "X", "Y", "Z").contains(s)).count() >= 3) {
            long groupSkus = skus.stream().filter(s -> Arrays.asList("S", "T", "X", "Y", "Z").contains(s)).count();
            if(groupSkus >= 3) {
                totalPrice += 45;

                int removed = 0;
                while (removed < 3 && skus.contains("Z")) {
                    skus.remove("Z");
                    removed++;
                }
                while (removed < 3 && skus.contains("Y")) {
                    skus.remove("Y");
                    removed++;
                }
                while (removed < 3 && skus.contains("S")) {
                    skus.remove("S");
                    removed++;
                }
                while (removed < 3 && skus.contains("T")) {
                    skus.remove("T");
                    removed++;
                }
                while (removed < 3 && skus.contains("X")) {
                    skus.remove("X");
                    removed++;
                }
            }
        }

        totalPrice += xProductsAndSameOneFree(skus, 2, "F", 1);
        totalPrice += xProductsAndSameOneFree(skus, 3, "U", 1);

        totalPrice += xProductsAndOtherOneFree(skus, 2, "E", "B");
        totalPrice += xProductsAndOtherOneFree(skus, 3, "N", "M");
        totalPrice += xProductsAndOtherOneFree(skus, 3, "R", "Q");

        totalPrice += singleThresholdScheme(skus, "B", 2, 45);
        totalPrice += singleThresholdScheme(skus, "K", 2, 120);
        totalPrice += singleThresholdScheme(skus, "P", 5, 200);
        totalPrice += singleThresholdScheme(skus, "Q", 3, 80);

        totalPrice += differentThresholdsScheme(skus, "A", 3, 130, 5, 200);
        totalPrice += differentThresholdsScheme(skus, "H", 5, 45, 10, 80);
        totalPrice += differentThresholdsScheme(skus, "V", 2, 90, 3, 130);

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

    private int singleThresholdScheme(List<String> skus, String sku, int threshold, int specialPrice) {
        int priceIncrement = 0;

        while(countSku(skus, sku) >= threshold) {
            if(countSku(skus, sku) >= threshold) {
                priceIncrement += specialPrice;
                for(int i = 0; i < threshold; i++) {
                    skus.remove(sku);
                }
            }
        }
        return priceIncrement;
    }

    private int xProductsAndSameOneFree(List<String> skus, int bought, String sku, int free) {
        int priceIncrement = 0;
        int numberOfAllSpecialSkus = bought + free;

        while(countSku(skus, sku) >= numberOfAllSpecialSkus) {
            if(countSku(skus, sku) >= numberOfAllSpecialSkus) {
//				long howManyFs = countSku(skus, "F");
//				long numberOfPairs = howManyFs % 2;
//				for(int i = 0; i < numberOfPairs; i++) {
//					skus.remove("F");
//				}

                priceIncrement += getPriceOfSingleProduct(sku) * bought;
                for(int i = 0; i < numberOfAllSpecialSkus; i++) {
                    skus.remove(sku);
                }
            }
        }
        return priceIncrement;
    }

    private int xProductsAndOtherOneFree(List<String> skus, int threshold1, String skuBought, String skuFree) {
        int priceIncrement = 0;

        while(countSku(skus, skuBought) >= threshold1) {
            if(countSku(skus, skuBought) >= threshold1) {
                priceIncrement += getPriceOfSingleProduct(skuBought) * threshold1;
                for(int i = 0; i < threshold1; i++) {
                    skus.remove(skuBought);
                }

                skus.remove(skuFree);
            }
        }
        return priceIncrement;
    }

    // ORDER MATTERS (it should be ascending)
    private int differentThresholdsScheme(List<String> skus, String sku, int threshold1, int price1, int threshold2, int price2) {
        int priceIncrement = 0;
        while(countSku(skus, sku) >= threshold1) {
            if(countSku(skus, sku) >= threshold2) {
                priceIncrement += price2;
                for(int i = 0; i < threshold2; i++) {
                    skus.remove(sku);
                }
            } else {
                priceIncrement += price1;
                for(int i = 0; i < threshold1; i++) {
                    skus.remove(sku);
                }
            }
        }

        return priceIncrement;
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
        HashMap<String, Integer> catalogue = new HashMap<>();
        catalogue.put("A", 50);
        catalogue.put("B", 30);
        catalogue.put("C", 20);
        catalogue.put("D", 15);
        catalogue.put("E", 40);
        catalogue.put("F", 10);
        catalogue.put("G", 20);
        catalogue.put("H", 10);
        catalogue.put("I", 35);
        catalogue.put("J", 60);
        catalogue.put("K", 70);
        catalogue.put("L", 90);
        catalogue.put("M", 15);
        catalogue.put("N", 40);
        catalogue.put("O", 10);
        catalogue.put("P", 50);
        catalogue.put("Q", 30);
        catalogue.put("R", 50);
        catalogue.put("S", 20);
        catalogue.put("T", 20);
        catalogue.put("U", 40);
        catalogue.put("V", 50);
        catalogue.put("W", 20);
        catalogue.put("X", 17);
        catalogue.put("Y", 20);
        catalogue.put("Z", 21);

        Integer integer = catalogue.get(sku);
        if(integer == null) throw new IllegalSkuException(sku);
        return integer;
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
