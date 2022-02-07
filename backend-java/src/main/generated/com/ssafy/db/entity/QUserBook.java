package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserBook is a Querydsl query type for UserBook
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserBook extends EntityPathBase<UserBook> {

    private static final long serialVersionUID = -255011050L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserBook userBook = new QUserBook("userBook");

    public final QBookDetail bookDetail;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QUser user;

    public QUserBook(String variable) {
        this(UserBook.class, forVariable(variable), INITS);
    }

    public QUserBook(Path<? extends UserBook> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserBook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserBook(PathMetadata metadata, PathInits inits) {
        this(UserBook.class, metadata, inits);
    }

    public QUserBook(Class<? extends UserBook> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bookDetail = inits.isInitialized("bookDetail") ? new QBookDetail(forProperty("bookDetail")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

