package topinterviewquestions;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Problem_2115_FindAllPossibleRecipesfromGivenSupplies_G {

    //https://leetcode.com/problems/find-all-possible-recipes-from-given-supplies/discuss/1646622/C%2B%2B-oror-topological-sort-oror-Kahn's-Algo
    //topology sort
    class Solution {
        public List findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {

            HashMap<String, Integer> recIndegreeMap = new HashMap<>();
            HashMap<String, ArrayList<String>> ingredientToRecipes = new HashMap<>();
            int n = recipes.length;
            HashSet<String> suppliesSet = new HashSet<>();

            for (int i = 0; i < supplies.length; i++) {
                suppliesSet.add(supplies[i]);
            }

            for (int i = 0; i < n; i++) {
                recIndegreeMap.put(recipes[i], i);
            }

            int[] indegree = new int[n];
            for (int i = 0; i < n; i++) {
                for (String str : ingredients.get(i)) {
                    if (suppliesSet.contains(str) == false) {
                        ingredientToRecipes.putIfAbsent(str, new ArrayList<>());
                        ArrayList<String> list = ingredientToRecipes.get(str);
                        list.add(recipes[i]);
                        indegree[i]++;
                    }
                }
            }

            Queue<String> q = new LinkedList<>();
            for (int i = 0; i < n; i++) {
                if (indegree[i] == 0) {
                    q.add(recipes[i]);
                }
            }
            List<String> ans = new ArrayList<>();
            while (!q.isEmpty()) {
                String node = q.peek();
                q.poll();
                ans.add(node);
                if (ingredientToRecipes.containsKey(node)) {
                    for (String str : ingredientToRecipes.get(node)) {
                        indegree[recIndegreeMap.get(str)]--;
                        if (indegree[recIndegreeMap.get(str)] == 0)
                            q.add(str);
                    }
                }
            }

            return ans;
        }
    }



    //Topological sort https://leetcode.com/problems/find-all-possible-recipes-from-given-supplies/discuss/1646584/JavaPython-3-Toplogical-Sort-w-brief-explanation.
    //For each recipe, count its non-available ingredients as in degree; Store (non-available ingredient, dependent recipes) as HashMap;
    //Store all 0-in-degree recipes into a list as the starting points of topological sort;
    //Use topogical sort to decrease the in degree of recipes, whenever the in-degree reaches 0, add it to return list.
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        List<String> res = new ArrayList<>();
        // Put all supplies into HashSet.
        Set<String> available = new HashSet<>();
        for (String s : supplies) {
            available.add(s);
        }
        Map<String, Set<String>> ingredientToRecipes = new HashMap<>();
        Map<String, Integer> inDegree = new HashMap<>();
        for (int i = 0; i < recipes.length; ++i) {
            int nonAvailable = 0;
            for (String s1 : ingredients.get(i)) {
                if (!available.contains(s1)) {
                    if (!ingredientToRecipes.containsKey(s1)) {
                        ingredientToRecipes.put(s1, new HashSet<>());
                    }
                    ingredientToRecipes.get(s1).add(recipes[i]);
                    //ingredientToRecipes.computeIfAbsent(s1, s -> new HashSet<>()).add(recipes[i]);
                    //或者用上面的一行代替前面的几行
                    ++nonAvailable;
                }
            }
            if (nonAvailable == 0) {
                res.add(recipes[i]);
            }else {
                inDegree.put(recipes[i], nonAvailable);
            }
        }
        // Toplogical Sort.
        for (int i = 0; i < res.size(); ++i) {
            String recipe = res.get(i);
            if (ingredientToRecipes.containsKey(recipe)) {
                for (String rcp : ingredientToRecipes.remove(recipe)) {
                    if (inDegree.merge(rcp, -1, Integer::sum) == 0) {
                        res.add(rcp);
                    }
                }
            }
        }
        return res;
    }
}
