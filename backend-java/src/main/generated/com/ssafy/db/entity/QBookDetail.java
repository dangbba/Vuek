package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBookDetail is a Querydsl query type for BookDetail
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBookDetail extends EntityPathBase<BookDetail> {

    private static final long serialVersionUID = 499998172L;

    public static final QBookDetail bookDetail = new QBookDetail("bookDetail");

    public final StringPath author = createString("author");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath overview = createString("overview");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath publish_date = createString("publish_date");

    public final StringPath publisher = createString("publisher");

    public final StringPath sail_status = createString("sail_status");

    public final StringPath title = createString("title");

    public final StringPath title_url = createString("title_url");

    public QBookDetail(String variable) {
        super(BookDetail.class, forVariable(variable));
    }

    public QBookDetail(Path<? extends BookDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBookDetail(PathMetadata metadata) {
        super(BookDetail.class, metadata);
    }

}

