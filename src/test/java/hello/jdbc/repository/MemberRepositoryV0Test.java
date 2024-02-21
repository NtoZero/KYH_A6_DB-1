package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @DisplayName("JDBC를 활용해 Member 데이터를 입력할 수 있다.")
    @Test
    void crud() throws SQLException {
        //save
        Member member = new Member("memberV0", 10000);
        repository.save(member);
    }

    @DisplayName("JDBC를 활용해 Member 데이터를 조회할 수 있다.")
    @Test
    void find() throws SQLException {
        //given
        Member member = new Member("memberV1", 10000);
        repository.save(member);

        //when
        Member findMember = repository.findById("memberV1");
        log.info("findMember={}", findMember);

        //then
        assertThat(findMember).isEqualTo(member);
    }
}