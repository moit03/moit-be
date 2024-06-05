package com.sparta.moit.global.util.pagination;

import java.util.List;

public class ListPaginator<T> implements Paginator<T> {
    @Override
    public boolean hasNextPage(List<T> list, int pageSize) {
        return list.size() > pageSize;
    }
}
