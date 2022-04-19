package com.github.calebwhiting.runelite.api.data;

import com.github.calebwhiting.runelite.api.LazyInitializer;
import net.runelite.api.*;
import net.runelite.api.widgets.WidgetID;
import org.intellij.lang.annotations.Language;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Queries ID mapping classes (ItemID, NpcID, ObjectID, WidgetID, etc)
 */
public class IDQuery {

    private final Set<Integer> results;
    private final Map<String, Integer> nameToIdMap;
    private final Map<Integer, String> idToNameMap;

    private static final LazyInitializer<IDQuery> ITEM_DATABASE = new LazyInitializer<IDQuery>() {
        @Override
        protected IDQuery create() {
            return new IDQuery(ItemID.class);
        }
    };

    private static final LazyInitializer<IDQuery> NPC_DATABASE = new LazyInitializer<IDQuery>() {
        @Override
        protected IDQuery create() {
            return new IDQuery(NpcID.class);
        }
    };

    private static final LazyInitializer<IDQuery> OBJECT_DATABASE = new LazyInitializer<IDQuery>() {
        @Override
        protected IDQuery create() {
            return new IDQuery(ObjectID.class);
        }
    };

    private static final LazyInitializer<IDQuery> WIDGET_DATABASE = new LazyInitializer<IDQuery>() {
        @Override
        protected IDQuery create() {
            return new IDQuery(WidgetID.class);
        }
    };

    private static final LazyInitializer<IDQuery> SCRIPT_DATABASE = new LazyInitializer<IDQuery>() {
        @Override
        protected IDQuery create() {
            return new IDQuery(ScriptID.class);
        }
    };

    private static final LazyInitializer<IDQuery> ANIMATION_DATABASE = new LazyInitializer<IDQuery>() {
        @Override
        protected IDQuery create() {
            return new IDQuery(AnimationID.class);
        }
    };

    private static final LazyInitializer<IDQuery> VARBIT_DATABASE = new LazyInitializer<IDQuery>() {
        @Override
        protected IDQuery create() {
            return new IDQuery(Varbits.class);
        }
    };

    public static IDQuery ofItems() {
        return ITEM_DATABASE.get().copy();
    }

    public static IDQuery ofNPCs() {
        return NPC_DATABASE.get().copy();
    }

    public static IDQuery ofObjects() {
        return OBJECT_DATABASE.get().copy();
    }

    public static IDQuery ofAnimations() {
        return ANIMATION_DATABASE.get().copy();
    }

    public static IDQuery ofVarbits() {
        return VARBIT_DATABASE.get().copy();
    }

    public static IDQuery ofWidgets() {
        return WIDGET_DATABASE.get().copy();
    }

    public static IDQuery ofScripts() {
        return SCRIPT_DATABASE.get().copy();
    }

    public IDQuery(Class<?> databaseClass) {
        this.nameToIdMap = new HashMap<>();
        this.idToNameMap = new HashMap<>();
        this.results = new HashSet<>();
        loadSymbols(databaseClass, this.nameToIdMap, this.idToNameMap);
    }

    private IDQuery(Map<String, Integer> nameToIdMap, Map<Integer, String> idToNameMap, Set<Integer> results) {
        this.nameToIdMap = nameToIdMap;
        this.idToNameMap = idToNameMap;
        this.results = results;
    }

    public IDQuery query(@Language("RegExp") String regex) {
        return query(Pattern.compile(regex), true);
    }

    public IDQuery query(@Language("RegExp") String regex, boolean stripNumberSuffix) {
        return query(Pattern.compile(regex), stripNumberSuffix);
    }

    private boolean matchQuery(Matcher matcher, boolean stripNumberSuffix, String name) {
        String stripped = stripNumberSuffix ? name.replaceAll("_[0-9]*$", "") : name;
        matcher.reset(stripped);
        return matcher.matches();
    }

    public IDQuery query(Pattern regex) {
        return query(regex, true);
    }

    public IDQuery query(Pattern regex, boolean stripNumberSuffix) {
        Matcher matcher = regex.matcher("");
        Set<Integer> results = new HashSet<>(this.results);
        nameToIdMap.entrySet()
                .stream()
                .filter(s -> matchQuery(matcher, stripNumberSuffix, s.getKey()))
                .map(Map.Entry::getValue)
                .forEach(results::add);
        return new IDQuery(this.nameToIdMap, this.idToNameMap, results);
    }

    public String getNameString(int[] ids) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ids.length; i++) {
            if (i > 0) {
                sb.append("|");
            }
            sb.append(getNameString(ids[i]));
        }
        return sb.toString();
    }

    public String getNameString(int id) {
        return idToNameMap.get(id);
    }

    public IDs results() {
        return new IDs(this.results);
    }

    public int[] ids() {
        return results().build();
    }

    public IDQuery copy() {
        return new IDQuery(this.nameToIdMap, this.idToNameMap, new HashSet<>(this.results));
    }

    private static void loadSymbols(
            Class<?> databaseClass,
            Map<String, Integer> nameToIdMap,
            Map<Integer, String> idToNameMap) {
        Field[] fields = databaseClass.getFields();
        for (Field field : fields) {
            if ((field.getModifiers() & Modifier.STATIC) == 0) {
                continue;
            }
            if (field.getType() != int.class) {
                continue;
            }
            try {
                int value = field.getInt(null);
                nameToIdMap.put(field.getName(), value);
                idToNameMap.put(value, field.getName());
            } catch (ReflectiveOperationException ignore) {

            }
        }
    }
}
