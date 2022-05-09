package com.github.calebwhiting.runelite.data;

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
public class IDQuery
{

	private static final LazyInitializer<IDQuery> ITEM_DATABASE = new LazyInitializer<IDQuery>()
	{
		@Override
		protected IDQuery create()
		{
			return new IDQuery(ItemID.class);
		}
	};

	private static final LazyInitializer<IDQuery> NPC_DATABASE = new LazyInitializer<IDQuery>()
	{
		@Override
		protected IDQuery create()
		{
			return new IDQuery(NpcID.class);
		}
	};

	private static final LazyInitializer<IDQuery> OBJECT_DATABASE = new LazyInitializer<IDQuery>()
	{
		@Override
		protected IDQuery create()
		{
			return new IDQuery(ObjectID.class);
		}
	};

	private static final LazyInitializer<IDQuery> WIDGET_DATABASE = new LazyInitializer<IDQuery>()
	{
		@Override
		protected IDQuery create()
		{
			return new IDQuery(WidgetID.class);
		}
	};

	private static final LazyInitializer<IDQuery> SCRIPT_DATABASE = new LazyInitializer<IDQuery>()
	{
		@Override
		protected IDQuery create()
		{
			return new IDQuery(ScriptID.class);
		}
	};

	private static final LazyInitializer<IDQuery> ANIMATION_DATABASE = new LazyInitializer<IDQuery>()
	{
		@Override
		protected IDQuery create()
		{
			return new IDQuery(AnimationID.class);
		}
	};

	private static final LazyInitializer<IDQuery> VARBIT_DATABASE = new LazyInitializer<IDQuery>()
	{
		@Override
		protected IDQuery create()
		{
			return new IDQuery(Varbits.class);
		}
	};

	private final Set<Integer> results;

	private final Map<String, Integer> nameToIdMap;

	private final Map<Integer, String> idToNameMap;

	private final Class<?> databaseClass;

	public IDQuery(Class<?> databaseClass)
	{
		this.nameToIdMap = new HashMap<>();
		this.idToNameMap = new HashMap<>();
		this.results = new HashSet<>();
		this.databaseClass = databaseClass;
		loadSymbols(databaseClass, this.nameToIdMap, this.idToNameMap);
	}

	private IDQuery(
			Map<String, Integer> nameToIdMap,
			Map<Integer, String> idToNameMap,
			Set<Integer> results,
			Class<?> databaseClass)
	{
		this.nameToIdMap = nameToIdMap;
		this.idToNameMap = idToNameMap;
		this.results = results;
		this.databaseClass = databaseClass;
	}

	public static IDQuery ofItems()
	{
		return ITEM_DATABASE.get().copy();
	}

	public static IDQuery ofNPCs()
	{
		return NPC_DATABASE.get().copy();
	}

	public static IDQuery ofObjects()
	{
		return OBJECT_DATABASE.get().copy();
	}

	public static IDQuery ofAnimations()
	{
		return ANIMATION_DATABASE.get().copy();
	}

	public static IDQuery ofVarbits()
	{
		return VARBIT_DATABASE.get().copy();
	}

	public static IDQuery ofWidgets()
	{
		return WIDGET_DATABASE.get().copy();
	}

	public static IDQuery ofScripts()
	{
		return SCRIPT_DATABASE.get().copy();
	}

	private static void loadSymbols(
			Class<?> databaseClass, Map<String, Integer> nameToIdMap, Map<Integer, String> idToNameMap)
	{
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

	public Class<?> getDatabaseClass()
	{
		return this.databaseClass;
	}

	public IDQuery query(@Language("RegExp") String regex)
	{
		return this.query(Pattern.compile(regex), true);
	}

	public IDQuery query(@Language("RegExp") String regex, boolean stripNumberSuffix)
	{
		return this.query(Pattern.compile(regex), stripNumberSuffix);
	}

	private boolean matchQuery(Matcher matcher, boolean stripNumberSuffix, String name)
	{
		String stripped = stripNumberSuffix ? name.replaceAll("_[0-9]*$", "") : name;
		matcher.reset(stripped);
		return matcher.matches();
	}

	public IDQuery query(Pattern regex)
	{
		return this.query(regex, true);
	}

	public IDQuery query(Pattern regex, boolean stripNumberSuffix)
	{
		Matcher matcher = regex.matcher("");
		Set<Integer> results = new HashSet<>(this.results);
		this.nameToIdMap.entrySet()
						.stream()
						.filter(s -> this.matchQuery(matcher, stripNumberSuffix, s.getKey()))
						.map(Map.Entry::getValue)
						.forEach(results::add);
		return new IDQuery(this.nameToIdMap, this.idToNameMap, results, this.databaseClass);
	}

	public String getNameString(int id)
	{
		return this.idToNameMap.get(id);
	}

	public IDs results()
	{
		return new IDs(this.results);
	}

	public int[] ids()
	{
		return this.results().build();
	}

	public IDQuery copy()
	{
		return new IDQuery(this.nameToIdMap, this.idToNameMap, new HashSet<>(this.results), this.databaseClass);
	}

}
