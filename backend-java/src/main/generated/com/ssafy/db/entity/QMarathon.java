package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMarathon is a Querydsl query type for Marathon
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMarathon extends EntityPathBase<Marathon> {

    private static final long serialVersionUID = 250173016L;

    public static final QMarathon marathon = new QMarathon("marathon");

    public final NumberPath<Integer> goalPages = createNumber("goalPages", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> nowPages = createNumber("nowPages", Integer.class);

    public final StringPath userId = createString("userId");

    public QMarathon(String variable) {
        super(Marathon.class, forVariable(variable));
    }

    public QMarathon(Path<? extends Marathon> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMarathon(PathMetadata metadata) {
        super(Marathon.class, metadata);
    }

}

