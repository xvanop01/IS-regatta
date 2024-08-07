package com.xvanop01.isregatta.user.tableDataService;

import com.xvanop01.isregatta.api.user.model.UserDetailDto;
import com.xvanop01.isregatta.base.support.template.TableData;
import com.xvanop01.isregatta.base.support.template.TableDataService;
import com.xvanop01.isregatta.user.mapper.UserMapper;
import com.xvanop01.isregatta.user.model.User;
import com.xvanop01.isregatta.user.model.User_;
import com.xvanop01.isregatta.user.repository.UserRepository;
import com.xvanop01.isregatta.user.tableDataService.filter.UserTableDataFilter;

/**
 * UserTableDataService
 * Servis pre tabulky s pouzivatelmi
 * @author 2024 Peter Vano
 */
@TableData("user-table")
public class UserTableDataService
        extends TableDataService<User, UserRepository, UserTableDataFilter, UserDetailDto, UserMapper> {

    public UserTableDataService(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected void doFilter(Object filter) {
        if (filter instanceof UserTableDataFilter f) {
            // filtrovanie podla pouzivatelskeho mena
            if (f.username != null && !f.username.isEmpty()) {
                String searchFormatted = "%" + f.username.toLowerCase() + "%";
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(User_.username), searchFormatted)
                );
            }
            // filtrovanie podla civilneho mena
            if (f.name != null && !f.name.isEmpty()) {
                String searchFormatted = "%" + f.name.toLowerCase() + "%";
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(User_.fullName), searchFormatted)
                );
            }
            // filtrovanie podla e-mailu
            if (f.email != null && !f.email.isEmpty()) {
                String searchFormatted = "%" + f.email.toLowerCase() + "%";
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(User_.email), searchFormatted)
                );
            }
        }
    }
}
