package phattrienungdungvoij2ee.bai5_qlsp.dto;

import phattrienungdungvoij2ee.bai5_qlsp.model.Product;

public record CartLineView(Product product, int quantity, long lineTotal) {
}
