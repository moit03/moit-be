package com.sparta.moit.global.util.pagination;

import java.util.List;

public interface Paginator<T> {
    boolean hasNextPage(List<T> list, int pageSize);
}
