package phattrienungdungvoij2ee.bai5_qlsp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "Tên sản phẩm không được để trống")
	@Column(name = "name", nullable = false, length = 255)
	private String name;

	@Column(name = "image", length = 255)
	private String image;

	@NotNull(message = "Giá sản phẩm không được để trống")
	@Min(value = 1, message = "Giá sản phẩm không được nhỏ hơn 1")
	@Max(value = 9_999_999_999_999L, message = "Giá sản phẩm vượt ngưỡng cho phép")
	@Column(name = "price", nullable = false)
	private Long price;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
}
