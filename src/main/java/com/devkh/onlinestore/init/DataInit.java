package com.devkh.onlinestore.init;

import com.devkh.onlinestore.api.product.Category;
import com.devkh.onlinestore.api.product.CategoryRepository;
import com.devkh.onlinestore.api.user.Authority;
import com.devkh.onlinestore.api.user.AuthorityRepository;
import com.devkh.onlinestore.api.user.Role;
import com.devkh.onlinestore.api.user.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void init() {

//        categoryRepository.save(Category.builder()
//                .name("Electronic")
//                .description("Electronic")
//                .build());
//        categoryRepository.save(Category.builder()
//                .name("Technology")
//                .description("Technology")
//                .build());
//        categoryRepository.save(Category.builder()
//                .name("LifeStyle")
//                .description("LifeStyle")
//                .build());
//
//        Authority readProduct = Authority.builder().name("product:read").build();
//        Authority writeProduct = Authority.builder().name("product:write").build();
//        Authority deleteProduct = Authority.builder().name("product:delete").build();
//        Authority updateProduct = Authority.builder().name("product:update").build();
//        Set<Authority> productAuthorities = Set.of(
//                readProduct, writeProduct, deleteProduct, updateProduct
//        );
//        authorityRepository.saveAll(productAuthorities);
//
//        Authority readUser = Authority.builder().name("user:read").build();
//        Authority writeUser = Authority.builder().name("user:write").build();
//        Authority deleteUser = Authority.builder().name("user:delete").build();
//        Authority updateUser = Authority.builder().name("user:update").build();
//        Set<Authority> userAuthorities = Set.of(
//                readUser, writeUser, deleteUser, updateUser
//        );
//        authorityRepository.saveAll(userAuthorities);
//
//        Set<Authority> fullAuthorities = new HashSet<>() {{
//            addAll(userAuthorities);
//            addAll(productAuthorities);
//        }};
//
//        Role adminRole = Role.builder()
//                .name("ADMIN")
//                .authorities(fullAuthorities)
//                .build();
//        Role staffRole = Role.builder().name("STAFF")
//                .authorities(productAuthorities)
//                .build();
//        Role customerRole = Role.builder()
//                .name("CUSTOMER")
//                .authorities(Set.of(
//                        writeUser, readUser, updateUser,
//                        readProduct
//                ))
//                .build();
//        roleRepository.saveAll(List.of(adminRole, staffRole, customerRole));

    }

}
