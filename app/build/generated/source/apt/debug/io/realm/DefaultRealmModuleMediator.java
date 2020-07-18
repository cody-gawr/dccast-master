package io.realm;


import android.util.JsonReader;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

@io.realm.annotations.RealmModule
class DefaultRealmModuleMediator extends RealmProxyMediator {

    private static final Set<Class<? extends RealmModel>> MODEL_CLASSES;
    static {
        Set<Class<? extends RealmModel>> modelClasses = new HashSet<Class<? extends RealmModel>>();
        modelClasses.add(notq.dccast.model.ModelSearchHistory.class);
        modelClasses.add(notq.dccast.model.category.CategoryItem.class);
        modelClasses.add(notq.dccast.model.user.ModelUser.class);
        modelClasses.add(notq.dccast.model.user.ModelChildUser.class);
        MODEL_CLASSES = Collections.unmodifiableSet(modelClasses);
    }

    @Override
    public Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap() {
        Map<Class<? extends RealmModel>, OsObjectSchemaInfo> infoMap = new HashMap<Class<? extends RealmModel>, OsObjectSchemaInfo>();
        infoMap.put(notq.dccast.model.ModelSearchHistory.class, io.realm.ModelSearchHistoryRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(notq.dccast.model.category.CategoryItem.class, io.realm.CategoryItemRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(notq.dccast.model.user.ModelUser.class, io.realm.ModelUserRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(notq.dccast.model.user.ModelChildUser.class, io.realm.ModelChildUserRealmProxy.getExpectedObjectSchemaInfo());
        return infoMap;
    }

    @Override
    public ColumnInfo createColumnInfo(Class<? extends RealmModel> clazz, OsSchemaInfo schemaInfo) {
        checkClass(clazz);

        if (clazz.equals(notq.dccast.model.ModelSearchHistory.class)) {
            return io.realm.ModelSearchHistoryRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(notq.dccast.model.category.CategoryItem.class)) {
            return io.realm.CategoryItemRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(notq.dccast.model.user.ModelUser.class)) {
            return io.realm.ModelUserRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(notq.dccast.model.user.ModelChildUser.class)) {
            return io.realm.ModelChildUserRealmProxy.createColumnInfo(schemaInfo);
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public List<String> getFieldNames(Class<? extends RealmModel> clazz) {
        checkClass(clazz);

        if (clazz.equals(notq.dccast.model.ModelSearchHistory.class)) {
            return io.realm.ModelSearchHistoryRealmProxy.getFieldNames();
        }
        if (clazz.equals(notq.dccast.model.category.CategoryItem.class)) {
            return io.realm.CategoryItemRealmProxy.getFieldNames();
        }
        if (clazz.equals(notq.dccast.model.user.ModelUser.class)) {
            return io.realm.ModelUserRealmProxy.getFieldNames();
        }
        if (clazz.equals(notq.dccast.model.user.ModelChildUser.class)) {
            return io.realm.ModelChildUserRealmProxy.getFieldNames();
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public String getTableName(Class<? extends RealmModel> clazz) {
        checkClass(clazz);

        if (clazz.equals(notq.dccast.model.ModelSearchHistory.class)) {
            return io.realm.ModelSearchHistoryRealmProxy.getTableName();
        }
        if (clazz.equals(notq.dccast.model.category.CategoryItem.class)) {
            return io.realm.CategoryItemRealmProxy.getTableName();
        }
        if (clazz.equals(notq.dccast.model.user.ModelUser.class)) {
            return io.realm.ModelUserRealmProxy.getTableName();
        }
        if (clazz.equals(notq.dccast.model.user.ModelChildUser.class)) {
            return io.realm.ModelChildUserRealmProxy.getTableName();
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E newInstance(Class<E> clazz, Object baseRealm, Row row, ColumnInfo columnInfo, boolean acceptDefaultValue, List<String> excludeFields) {
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        try {
            objectContext.set((BaseRealm) baseRealm, row, columnInfo, acceptDefaultValue, excludeFields);
            checkClass(clazz);

            if (clazz.equals(notq.dccast.model.ModelSearchHistory.class)) {
                return clazz.cast(new io.realm.ModelSearchHistoryRealmProxy());
            }
            if (clazz.equals(notq.dccast.model.category.CategoryItem.class)) {
                return clazz.cast(new io.realm.CategoryItemRealmProxy());
            }
            if (clazz.equals(notq.dccast.model.user.ModelUser.class)) {
                return clazz.cast(new io.realm.ModelUserRealmProxy());
            }
            if (clazz.equals(notq.dccast.model.user.ModelChildUser.class)) {
                return clazz.cast(new io.realm.ModelChildUserRealmProxy());
            }
            throw getMissingProxyClassException(clazz);
        } finally {
            objectContext.clear();
        }
    }

    @Override
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return MODEL_CLASSES;
    }

    @Override
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E obj, boolean update, Map<RealmModel, RealmObjectProxy> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(notq.dccast.model.ModelSearchHistory.class)) {
            return clazz.cast(io.realm.ModelSearchHistoryRealmProxy.copyOrUpdate(realm, (notq.dccast.model.ModelSearchHistory) obj, update, cache));
        }
        if (clazz.equals(notq.dccast.model.category.CategoryItem.class)) {
            return clazz.cast(io.realm.CategoryItemRealmProxy.copyOrUpdate(realm, (notq.dccast.model.category.CategoryItem) obj, update, cache));
        }
        if (clazz.equals(notq.dccast.model.user.ModelUser.class)) {
            return clazz.cast(io.realm.ModelUserRealmProxy.copyOrUpdate(realm, (notq.dccast.model.user.ModelUser) obj, update, cache));
        }
        if (clazz.equals(notq.dccast.model.user.ModelChildUser.class)) {
            return clazz.cast(io.realm.ModelChildUserRealmProxy.copyOrUpdate(realm, (notq.dccast.model.user.ModelChildUser) obj, update, cache));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public void insert(Realm realm, RealmModel object, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

        if (clazz.equals(notq.dccast.model.ModelSearchHistory.class)) {
            io.realm.ModelSearchHistoryRealmProxy.insert(realm, (notq.dccast.model.ModelSearchHistory) object, cache);
        } else if (clazz.equals(notq.dccast.model.category.CategoryItem.class)) {
            io.realm.CategoryItemRealmProxy.insert(realm, (notq.dccast.model.category.CategoryItem) object, cache);
        } else if (clazz.equals(notq.dccast.model.user.ModelUser.class)) {
            io.realm.ModelUserRealmProxy.insert(realm, (notq.dccast.model.user.ModelUser) object, cache);
        } else if (clazz.equals(notq.dccast.model.user.ModelChildUser.class)) {
            io.realm.ModelChildUserRealmProxy.insert(realm, (notq.dccast.model.user.ModelChildUser) object, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insert(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(notq.dccast.model.ModelSearchHistory.class)) {
                io.realm.ModelSearchHistoryRealmProxy.insert(realm, (notq.dccast.model.ModelSearchHistory) object, cache);
            } else if (clazz.equals(notq.dccast.model.category.CategoryItem.class)) {
                io.realm.CategoryItemRealmProxy.insert(realm, (notq.dccast.model.category.CategoryItem) object, cache);
            } else if (clazz.equals(notq.dccast.model.user.ModelUser.class)) {
                io.realm.ModelUserRealmProxy.insert(realm, (notq.dccast.model.user.ModelUser) object, cache);
            } else if (clazz.equals(notq.dccast.model.user.ModelChildUser.class)) {
                io.realm.ModelChildUserRealmProxy.insert(realm, (notq.dccast.model.user.ModelChildUser) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(notq.dccast.model.ModelSearchHistory.class)) {
                    io.realm.ModelSearchHistoryRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(notq.dccast.model.category.CategoryItem.class)) {
                    io.realm.CategoryItemRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(notq.dccast.model.user.ModelUser.class)) {
                    io.realm.ModelUserRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(notq.dccast.model.user.ModelChildUser.class)) {
                    io.realm.ModelChildUserRealmProxy.insert(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, RealmModel obj, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(notq.dccast.model.ModelSearchHistory.class)) {
            io.realm.ModelSearchHistoryRealmProxy.insertOrUpdate(realm, (notq.dccast.model.ModelSearchHistory) obj, cache);
        } else if (clazz.equals(notq.dccast.model.category.CategoryItem.class)) {
            io.realm.CategoryItemRealmProxy.insertOrUpdate(realm, (notq.dccast.model.category.CategoryItem) obj, cache);
        } else if (clazz.equals(notq.dccast.model.user.ModelUser.class)) {
            io.realm.ModelUserRealmProxy.insertOrUpdate(realm, (notq.dccast.model.user.ModelUser) obj, cache);
        } else if (clazz.equals(notq.dccast.model.user.ModelChildUser.class)) {
            io.realm.ModelChildUserRealmProxy.insertOrUpdate(realm, (notq.dccast.model.user.ModelChildUser) obj, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(notq.dccast.model.ModelSearchHistory.class)) {
                io.realm.ModelSearchHistoryRealmProxy.insertOrUpdate(realm, (notq.dccast.model.ModelSearchHistory) object, cache);
            } else if (clazz.equals(notq.dccast.model.category.CategoryItem.class)) {
                io.realm.CategoryItemRealmProxy.insertOrUpdate(realm, (notq.dccast.model.category.CategoryItem) object, cache);
            } else if (clazz.equals(notq.dccast.model.user.ModelUser.class)) {
                io.realm.ModelUserRealmProxy.insertOrUpdate(realm, (notq.dccast.model.user.ModelUser) object, cache);
            } else if (clazz.equals(notq.dccast.model.user.ModelChildUser.class)) {
                io.realm.ModelChildUserRealmProxy.insertOrUpdate(realm, (notq.dccast.model.user.ModelChildUser) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(notq.dccast.model.ModelSearchHistory.class)) {
                    io.realm.ModelSearchHistoryRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(notq.dccast.model.category.CategoryItem.class)) {
                    io.realm.CategoryItemRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(notq.dccast.model.user.ModelUser.class)) {
                    io.realm.ModelUserRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(notq.dccast.model.user.ModelChildUser.class)) {
                    io.realm.ModelChildUserRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> clazz, Realm realm, JSONObject json, boolean update)
        throws JSONException {
        checkClass(clazz);

        if (clazz.equals(notq.dccast.model.ModelSearchHistory.class)) {
            return clazz.cast(io.realm.ModelSearchHistoryRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(notq.dccast.model.category.CategoryItem.class)) {
            return clazz.cast(io.realm.CategoryItemRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(notq.dccast.model.user.ModelUser.class)) {
            return clazz.cast(io.realm.ModelUserRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(notq.dccast.model.user.ModelChildUser.class)) {
            return clazz.cast(io.realm.ModelChildUserRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E createUsingJsonStream(Class<E> clazz, Realm realm, JsonReader reader)
        throws IOException {
        checkClass(clazz);

        if (clazz.equals(notq.dccast.model.ModelSearchHistory.class)) {
            return clazz.cast(io.realm.ModelSearchHistoryRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(notq.dccast.model.category.CategoryItem.class)) {
            return clazz.cast(io.realm.CategoryItemRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(notq.dccast.model.user.ModelUser.class)) {
            return clazz.cast(io.realm.ModelUserRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(notq.dccast.model.user.ModelChildUser.class)) {
            return clazz.cast(io.realm.ModelChildUserRealmProxy.createUsingJsonStream(realm, reader));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E createDetachedCopy(E realmObject, int maxDepth, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) realmObject.getClass().getSuperclass();

        if (clazz.equals(notq.dccast.model.ModelSearchHistory.class)) {
            return clazz.cast(io.realm.ModelSearchHistoryRealmProxy.createDetachedCopy((notq.dccast.model.ModelSearchHistory) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(notq.dccast.model.category.CategoryItem.class)) {
            return clazz.cast(io.realm.CategoryItemRealmProxy.createDetachedCopy((notq.dccast.model.category.CategoryItem) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(notq.dccast.model.user.ModelUser.class)) {
            return clazz.cast(io.realm.ModelUserRealmProxy.createDetachedCopy((notq.dccast.model.user.ModelUser) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(notq.dccast.model.user.ModelChildUser.class)) {
            return clazz.cast(io.realm.ModelChildUserRealmProxy.createDetachedCopy((notq.dccast.model.user.ModelChildUser) realmObject, 0, maxDepth, cache));
        }
        throw getMissingProxyClassException(clazz);
    }

}
