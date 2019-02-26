package br.com.kelvinsantiago.example.dto;

import br.com.kelvinsantiago.example.service.ModelMapperService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class ResultListDTO<T> {

    private long totalResults;
    private int totalPages;
    private List<T> result;


    public ResultListDTO(Page<T> resultPage,
                         ModelMapperService modelMapperService,
                         Class<T> tClass) {

        this.totalPages = resultPage.getTotalPages();
        this.totalResults = resultPage.getTotalElements();
        this.result = modelMapperService.toList(tClass,resultPage.getContent());
    }
}
