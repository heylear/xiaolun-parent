package cn.glor.xiaolun.app.common.page;

import java.util.List;

/**
 * Created by caosh on 2015/10/31.
 */
public class PageResult<T> {

    PageQuery pageQuery;

    private int totalRows;

    private int totalPages;

    private boolean hasNextPage;

    private List<T> dataList;

    private PageResult() {
    }

    public static <T> PageResult<T> newPageResult(PageQuery pageQuery, int totalRows, List<T> dataList) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.pageQuery = pageQuery;
        pageResult.totalRows = totalRows;
        pageResult.totalPages = (totalRows + pageQuery.getPageSize() - 1) / pageQuery.getPageSize();
        pageResult.hasNextPage = pageQuery.getPageNum() + 1 <= pageResult.totalPages;
        pageResult.dataList = dataList;
        return pageResult;
    }

    public PageQuery getPageQuery() {
        return pageQuery;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public List<T> getDataList() {
        return dataList;
    }
}
