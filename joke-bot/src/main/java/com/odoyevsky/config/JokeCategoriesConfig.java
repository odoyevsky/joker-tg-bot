package com.odoyevsky.config;

import com.odoyevsky.model.entity.Category;
import com.odoyevsky.service.CategoryService;
import lombok.Getter;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
@Getter
public class JokeCategoriesConfig {
    private CategoryService categoryService;
    private List<String> categoryGroups = new ArrayList<>();
    private List<List<String>> batchedCategories;

    public JokeCategoriesConfig(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    public boolean groupExists(String group){
        return categoryGroups.contains(group);
    }

    public List<String> getCategoryGroup(String group){
        return batchedCategories.get(categoryGroups.indexOf(group));
    }

    @EventListener
    public void init(ContextRefreshedEvent ctxRefreshedEvent) {
        batchedCategories = batches(
                categoryService.getCategories().stream().map(Category::getName).toList(),
                5
        );

        for (int i = 0; i < batchedCategories.size(); i++) {
            categoryGroups.add("Категория " + ((i) * 5 + 1) + "-" + (i + 1) * 5);
        }

    }

    public static List<List<String>> batches(List<String> source, int length) {
        int size = source.size();

        if (size == 0) return List.of();

        int fullChunks = (size - 1) / length;
        return IntStream.range(0, fullChunks + 1)
                .mapToObj(n -> source.subList(n * length, n == fullChunks ? size : (n + 1) * length))
                .toList();
    }
}
