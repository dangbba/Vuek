package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConference is a Querydsl query type for Conference
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QConference extends EntityPathBase<Conference> {

    private static final long serialVersionUID = -625543586L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConference conference = new QConference("conference");

    public final QBookDetail bookDetail;

    public final DateTimePath<java.time.LocalDateTime> callEndTime = createDateTime("callEndTime", java.time.LocalDateTime.class);

    public final StringPath callStartTime = createString("callStartTime");

    public final QConferenceType conferenceType;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> isActive = createNumber("isActive", Integer.class);

    public final ListPath<User, QUser> participant = this.<User, QUser>createList("participant", User.class, QUser.class, PathInits.DIRECT2);

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final StringPath title = createString("title");

    public final QUser user;

    public QConference(String variable) {
        this(Conference.class, forVariable(variable), INITS);
    }

    public QConference(Path<? extends Conference> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConference(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConference(PathMetadata metadata, PathInits inits) {
        this(Conference.class, metadata, inits);
    }

    public QConference(Class<? extends Conference> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bookDetail = inits.isInitialized("bookDetail") ? new QBookDetail(forProperty("bookDetail")) : null;
        this.conferenceType = inits.isInitialized("conferenceType") ? new QConferenceType(forProperty("conferenceType")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

