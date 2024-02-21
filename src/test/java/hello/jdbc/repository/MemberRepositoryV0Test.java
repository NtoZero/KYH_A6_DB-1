package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @DisplayName("JDBC를 활용해 Member 데이터를 업데이트 할 수 있다.")
    @Test
    void update() throws SQLException {
        //given
        Member member = new Member("memberV2", 10000);
        repository.save(member);
        Member findMember = repository.findById(member.getMemberId());
        assertThat(findMember).isEqualTo(member);

        //when
        repository.update(member.getMemberId(), 20000);

        //then
        Member updateMember = repository.findById(member.getMemberId());
        assertThat(updateMember.getMoney()).isEqualTo(20000);
    }

    @DisplayName("JDBC를 활용해 Member 데이터를 삭제할 수 있다.")
    @Test
    void delete() throws SQLException {
        //given
        Member member = new Member("memberV3", 10000);
        repository.save(member);

        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        assertThat(findMember).isEqualTo(member);

        repository.update(member.getMemberId(), 20000);
        Member updatedMember = repository.findById(member.getMemberId());
        assertThat(updatedMember.getMoney()).isEqualTo(20000);

        //when
        repository.delete(member.getMemberId());

        //then
        assertThatThrownBy(() -> repository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }
}