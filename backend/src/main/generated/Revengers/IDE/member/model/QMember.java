package Revengers.IDE.member.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -338064468L;

    public static final QMember member = new QMember("member1");

    public final DateTimePath<java.time.LocalDateTime> createMemberDate = createDateTime("createMemberDate", java.time.LocalDateTime.class);

    public final ListPath<Revengers.IDE.docker.model.Docker, Revengers.IDE.docker.model.QDocker> docker = this.<Revengers.IDE.docker.model.Docker, Revengers.IDE.docker.model.QDocker>createList("docker", Revengers.IDE.docker.model.Docker.class, Revengers.IDE.docker.model.QDocker.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memberId = createString("memberId");

    public final StringPath memberName = createString("memberName");

    public final StringPath password = createString("password");

    public final EnumPath<MemberRole> role = createEnum("role", MemberRole.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

