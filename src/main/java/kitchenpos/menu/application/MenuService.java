package kitchenpos.menu.application;

import kitchenpos.menu.domain.*;
import kitchenpos.menu.dto.MenuProductRequest;
import kitchenpos.menu.dto.MenuResponse;
import kitchenpos.product.application.ProductService;
import kitchenpos.menu.dto.MenuRequest;
import kitchenpos.product.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuValidator menuValidator;
    private final ProductService productService;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuValidator menuValidator,
            final ProductService productService
    ) {
        this.menuRepository = menuRepository;
        this.menuValidator = menuValidator;
        this.productService = productService;
    }

    @Transactional
    public MenuResponse create(final MenuRequest menuRequest) {
        menuValidator.existMenuGroup(menuRequest.getMenuGroupId());
        final Menu menu = menuRequest.toMenu();
        final List<MenuProduct> menuProducts = getMenuProducts(menuRequest.getMenuProducts());
        menu.addMenuProducts(menuProducts);
        menuValidator.validateOverPrice(menu);
        final Menu savedMenu = menuRepository.save(menu);

        return MenuResponse.from(savedMenu);
    }

    public List<MenuResponse> list() {
        return menuRepository.findAll()
                .stream()
                .map(MenuResponse::from)
                .collect(Collectors.toList());
    }

    private List<MenuProduct> getMenuProducts(List<MenuProductRequest> requests) {
        List<MenuProduct> result = new ArrayList<>();
        for (final MenuProductRequest menuProductRequest : requests) {
            final Product product = productService.getProduct(menuProductRequest.getProductId());
            result.add(new MenuProduct(product.getId(), menuProductRequest.getQuantity()));
        }
        return result;
    }

    public Menu findById(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(IllegalArgumentException::new);
    }
}
