package Revengers.IDE.docker.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDocker is a Querydsl query type for Docker
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDocker extends EntityPathBase<Docker> {

    private static final long serialVersionUID = 389674348L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDocker docker = new QDocker("docker");

    public final StringPath containerId = createString("containerId");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath langType = createString("langType");

    public final Revengers.IDE.member.model.QMember member;

    public QDocker(String variable) {
        this(Docker.class, forVariable(variable), INITS);
    }

    public QDocker(Path<? extends Docker> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDocker(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDocker(PathMetadata metadata, PathInits inits) {
        this(Docker.class, metadata, inits);
    }

    public QDocker(Class<? extends Docker> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new Revengers.IDE.member.model.QMember(forProperty("member")) : null;
    }

}

