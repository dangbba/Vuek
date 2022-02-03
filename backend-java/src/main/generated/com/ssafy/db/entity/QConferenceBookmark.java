package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConferenceBookmark is a Querydsl query type for ConferenceBookmark
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QConferenceBookmark extends EntityPathBase<ConferenceBookmark> {

    private static final long serialVersionUID = 1944306100L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConferenceBookmark conferenceBookmark = new QConferenceBookmark("conferenceBookmark");

    public final QBookDetail bookDetail;

    public final QConference conference;

    public final StringPath content = createString("content");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QConferenceBookmark(String variable) {
        this(ConferenceBookmark.class, forVariable(variable), INITS);
    }

    public QConferenceBookmark(Path<? extends ConferenceBookmark> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConferenceBookmark(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConferenceBookmark(PathMetadata metadata, PathInits inits) {
        this(ConferenceBookmark.class, metadata, inits);
    }

    public QConferenceBookmark(Class<? extends ConferenceBookmark> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bookDetail = inits.isInitialized("bookDetail") ? new QBookDetail(forProperty("bookDetail")) : null;
        this.conference = inits.isInitialized("conference") ? new QConference(forProperty("conference"), inits.get("conference")) : null;
    }

}

