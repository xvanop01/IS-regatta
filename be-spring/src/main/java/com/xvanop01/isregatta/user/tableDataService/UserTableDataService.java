package com.xvanop01.isregatta.user.tableDataService;

import com.xvanop01.isregatta.api.user.model.UserDetailDto;
import com.xvanop01.isregatta.base.support.template.TableData;
import com.xvanop01.isregatta.base.support.template.TableDataService;
import com.xvanop01.isregatta.user.model.User;
import com.xvanop01.isregatta.user.repository.UserRepository;

@TableData("user-table")
public class UserTableDataService extends TableDataService<User, UserRepository, UserDetailDto, UserTableMapper> {

    public UserTableDataService(UserRepository repository, UserTableMapper mapper) {
        super(repository, mapper);
    }
}
