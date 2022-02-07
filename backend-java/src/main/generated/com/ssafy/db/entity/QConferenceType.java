package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QConferenceType is a Querydsl query type for ConferenceType
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QConferenceType extends EntityPathBase<ConferenceType> {

    private static final long serialVersionUID = 530619064L;

    public static final QConferenceType conferenceType = new QConferenceType("conferenceType");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public QConferenceType(String variable) {
        super(ConferenceType.class, forVariable(variable));
    }

    public QConferenceType(Path<? extends ConferenceType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConferenceType(PathMetadata metadata) {
        super(ConferenceType.class, metadata);
    }

}

