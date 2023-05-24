package com.example.autoconfig.member;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository @RequiredArgsConstructor
public class MemberRepository {

    public final JdbcTemplate jdbcTemplate;

    // 보토 리포지토리에 테이블을 생성하는 스크립트를 두지는 않음.
    public void initTable() {
        jdbcTemplate.execute("create table member(member_id varchar primary key, name varchar)");
    }

    public void save(Member member) {
        jdbcTemplate.update("insert into member(member_id, name) values(?,?)", member.getMemberId(),  member.getName());
    }

    public Member find(String memberId) {
        return jdbcTemplate.queryForObject("select member_id, name from member where member_id=?",
                        BeanPropertyRowMapper.newInstance(Member.class),
                        memberId
        );
    }

    public List<Member> findAll() {
        return jdbcTemplate.query("select member_id, name from member",
                BeanPropertyRowMapper.newInstance(Member.class));
    }

}
