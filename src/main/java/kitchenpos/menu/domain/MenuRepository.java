package kitchenpos.menu.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    public Long countByIdIn(final List<Long> ids);
}
