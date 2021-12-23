package com.denisio.app.utils;

public class FilterConfiguration {

    private final OrderBy orderBy;
    private final SortingDirection sortingDirection;

    private FilterConfiguration(FilterConfigurationBuilder builder){
        this.orderBy = builder.orderBy;
        this.sortingDirection = builder.sortingDirection;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public SortingDirection getSortingDirection() {
        return sortingDirection;
    }

    public static class FilterConfigurationBuilder{
        private OrderBy orderBy;
        private SortingDirection sortingDirection;

        public FilterConfigurationBuilder withOrderBy(OrderBy orderBy){
            this.orderBy = orderBy;
            return this;
        }

        public FilterConfigurationBuilder withSortingDirection(SortingDirection sortingDirection){
            this.sortingDirection = sortingDirection;
            return this;
        }

        public FilterConfiguration build(){
            return new FilterConfiguration(this);
        }

    }
}
