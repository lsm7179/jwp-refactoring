package kitchenpos.menu.domain;

import kitchenpos.menuGroup.domain.MenuGroup;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "menu_group_id", foreignKey = @ForeignKey(name = "fk_menu_product_menu"))
    private MenuGroup menuGroup;

    @Embedded
    private MenuProducts menuProducts = new MenuProducts();

    public Menu() {

    }

    public Menu(Long id, String name, BigDecimal price, MenuGroup menuGroup, List<MenuProduct> menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        menuProducts.stream()
                .forEach(menuProduct -> {
                    menuProduct.setMenu(this);
                    this.menuProducts.add(menuProduct);
                });
    }

    public Menu(String name, BigDecimal price, MenuGroup menuGroup, List<MenuProduct> menuProducts) {
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        menuProducts.stream()
                .forEach(menuProduct -> {
                    menuProduct.setMenu(this);
                    this.menuProducts.add(menuProduct);
                });
    }

    public Menu(String name, BigDecimal price, MenuGroup menuGroup) {
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public Long getMenuGroup() {
        return menuGroup.getId();
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts.getList();
    }

    public void setMenuProducts(List<MenuProduct> menuProducts) {
        menuProducts.stream()
                .forEach(menuProduct -> {
                    menuProduct.setMenu(this);
                    this.menuProducts.add(menuProduct);
                });
    }
}
