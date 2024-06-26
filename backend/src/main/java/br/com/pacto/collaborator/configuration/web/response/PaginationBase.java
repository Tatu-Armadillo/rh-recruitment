package br.com.pacto.collaborator.configuration.web.response;

import org.springframework.data.domain.Page;

public class PaginationBase {

    private int pagina;
    private int tamanhoPagina;
    private int totalPaginas;
    private long totalRegistros;

    public PaginationBase() { }

    public <T> PaginationBase(final Page<T> page) {
        this.setPageNumber(page.getNumber());
        this.setPageSize(page.getSize());
        this.setTotalPages(page.getTotalPages());
        this.setTotalElements(page.getTotalElements());
    }

    public int getPageNumber() {
        return this.pagina;
    }

    public void setPageNumber(final int pageNumber) {
        this.pagina = (pageNumber + 1);
    }

    public int getPageSize() {
        return this.tamanhoPagina;
    }

    public void setPageSize(final int pageSize) {
        this.tamanhoPagina = pageSize;
    }

    public int getTotalPages() {
        return this.totalPaginas;
    }

    public void setTotalPages(final int totalPages) {
        this.totalPaginas = totalPages;
    }

    public long getTotalElements() {
        return this.totalRegistros;
    }

    public void setTotalElements(final long totalElements) {
        this.totalRegistros = totalElements;
    }

}
