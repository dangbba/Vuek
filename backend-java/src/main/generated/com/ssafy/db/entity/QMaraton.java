package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMaraton is a Querydsl query type for Maraton
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMaraton extends EntityPathBase<Maraton> {

    private static final long serialVersionUID = 285164974L;

    public static final QMaraton maraton = new QMaraton("maraton");

    public final NumberPath<Integer> goal_pages = createNumber("goal_pages", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> now_pages = createNumber("now_pages", Integer.class);

    public final StringPath user_user_id = createString("user_user_id");

    public QMaraton(String variable) {
        super(Maraton.class, forVariable(variable));
    }

    public QMaraton(Path<? extends Maraton> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMaraton(PathMetadata metadata) {
        super(Maraton.class, metadata);
    }

}

