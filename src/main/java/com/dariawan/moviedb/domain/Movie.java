package com.dariawan.moviedb.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.validation.annotation.Validated;

// @Data
// @Accessors(chain = true)
@Validated
@Entity
@Table(name = "movie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
public class Movie implements Serializable {

    private static final long serialVersionUID = 6235798961366545815L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 255)
    private String title;
    
    // @Pattern(regexp ="^\\d{4}", message = "Year")
    private Integer releaseYear;
    
    private Integer runtimeMinutes;
    
    @Size(max = 255)
    private String tags;
    
    @Size(max = 50)
    private String lang;
    
    @Size(max = 50)
    private String country; 
}
