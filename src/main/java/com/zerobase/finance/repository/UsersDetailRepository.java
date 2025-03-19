package com.zerobase.finance.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.finance.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.zerobase.finance.entity.QUsers.users;

@Repository
@RequiredArgsConstructor
public class UsersDetailRepository {
    private final UsersRepository usersRepository;
    private final JPAQueryFactory queryFactory;
    public void signUP(Users user) {
        usersRepository.save(user);
    }

    public boolean checkSameUserId(String userId) {
        Users result =  queryFactory.selectFrom(users)
                .where(users.loginId.eq(userId))
                .fetchOne();
        return result != null;//유저 존재하면 true
    }
}
