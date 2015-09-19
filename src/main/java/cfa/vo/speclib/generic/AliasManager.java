package cfa.vo.speclib.generic;

import uk.ac.starlink.table.DescribedValue;
import uk.ac.starlink.table.StarTable;

import java.util.*;

/**
 * Created by Omar on 9/19/2015.
 */
public class AliasManager {
    private Map<String, List<String>> utypeMappings = Collections.synchronizedMap(new HashMap<String, List<String>>());

    private static AliasManager ourInstance = new AliasManager();

    public static AliasManager getInstance() {
        return ourInstance;
    }

    private AliasManager() {
    }

    // FIXME The logic in this method can most likely be improved.
    // This method should properly handle all the votable and fits cases.
    // Note that some cases (e.g. Target.pos double[2] vs (RA, DEC)) are more complicated
    // than just an alias.
    public DescribedValue findParamByUtype(StarTable table, String utype) {
        String newUtype = utype;
        for (DescribedValue param: (List<DescribedValue>) table.getParameters()) {
            //check utype as is
            if (utype.equals(param.getInfo().getUtype())) {
                return param;
            } else {
                // utype cannot be found, then remove the prefix and check the aliases
                if (utype.contains(":")) {
                    newUtype = utype.split(":")[1];
                }
                List<String> aliases = utypeMappings.get(newUtype);
                // now compare the namespace-less utype, or the aliases
                if (utype.equals(param.getInfo().getUtype()) ||
                        ((aliases != null) && (aliases.contains(param.getInfo().getUtype()) || aliases.contains(param.getInfo().getName())))) {
                    return param;
                }
            }
        }

        return null;
    }

    public void addAlias(String utype, String alias) {
        List<String> aliases = utypeMappings.get(utype);
        if (aliases == null) {
            aliases = new ArrayList();
            utypeMappings.put(utype, aliases);
        }
        aliases.add(alias);
    }
}