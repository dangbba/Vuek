package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMarathon is a Querydsl query type for Marathon
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMarathon extends EntityPathBase<Marathon> {

    private static final long serialVersionUID = 250173016L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMarathon marathon = new QMarathon("marathon");

    public final NumberPath<Integer> goalPages = createNumber("goalPages", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> nowPages = createNumber("nowPages", Integer.class);

    public final QUser user;

    public QMarathon(String variable) {
        this(Marathon.class, forVariable(variable), INITS);
    }

    public QMarathon(Path<? extends Marathon> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMarathon(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMarathon(PathMetadata metadata, PathInits inits) {
        this(Marathon.class, metadata, inits);
    }

    public QMarathon(Class<? extends Marathon> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

