package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsList;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.Property;
import io.realm.internal.ProxyUtils;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("all")
public class ModelSearchHistoryRealmProxy extends notq.dccast.model.ModelSearchHistory
    implements RealmObjectProxy, ModelSearchHistoryRealmProxyInterface {

    static final class ModelSearchHistoryColumnInfo extends ColumnInfo {
        long idIndex;
        long keywordIndex;

        ModelSearchHistoryColumnInfo(OsSchemaInfo schemaInfo) {
            super(2);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("ModelSearchHistory");
            this.idIndex = addColumnDetails("id", objectSchemaInfo);
            this.keywordIndex = addColumnDetails("keyword", objectSchemaInfo);
        }

        ModelSearchHistoryColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new ModelSearchHistoryColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final ModelSearchHistoryColumnInfo src = (ModelSearchHistoryColumnInfo) rawSrc;
            final ModelSearchHistoryColumnInfo dst = (ModelSearchHistoryColumnInfo) rawDst;
            dst.idIndex = src.idIndex;
            dst.keywordIndex = src.keywordIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("id");
        fieldNames.add("keyword");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private ModelSearchHistoryColumnInfo columnInfo;
    private ProxyState<notq.dccast.model.ModelSearchHistory> proxyState;

    ModelSearchHistoryRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (ModelSearchHistoryColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<notq.dccast.model.ModelSearchHistory>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.idIndex);
    }

    @Override
    public void realmSet$id(int value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'id' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$keyword() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.keywordIndex);
    }

    @Override
    public void realmSet$keyword(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.keywordIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.keywordIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.keywordIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.keywordIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("ModelSearchHistory");
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("keyword", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static ModelSearchHistoryColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new ModelSearchHistoryColumnInfo(schemaInfo);
    }

    public static String getTableName() {
        return "class_ModelSearchHistory";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static notq.dccast.model.ModelSearchHistory createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        notq.dccast.model.ModelSearchHistory obj = null;
        if (update) {
            Table table = realm.getTable(notq.dccast.model.ModelSearchHistory.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(notq.dccast.model.ModelSearchHistory.class), false, Collections.<String> emptyList());
                    obj = new io.realm.ModelSearchHistoryRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.ModelSearchHistoryRealmProxy) realm.createObjectInternal(notq.dccast.model.ModelSearchHistory.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.ModelSearchHistoryRealmProxy) realm.createObjectInternal(notq.dccast.model.ModelSearchHistory.class, json.getInt("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }

        final ModelSearchHistoryRealmProxyInterface objProxy = (ModelSearchHistoryRealmProxyInterface) obj;
        if (json.has("keyword")) {
            if (json.isNull("keyword")) {
                objProxy.realmSet$keyword(null);
            } else {
                objProxy.realmSet$keyword((String) json.getString("keyword"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static notq.dccast.model.ModelSearchHistory createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final notq.dccast.model.ModelSearchHistory obj = new notq.dccast.model.ModelSearchHistory();
        final ModelSearchHistoryRealmProxyInterface objProxy = (ModelSearchHistoryRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'id' to null.");
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("keyword")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$keyword((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$keyword(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
        }
        return realm.copyToRealm(obj);
    }

    public static notq.dccast.model.ModelSearchHistory copyOrUpdate(Realm realm, notq.dccast.model.ModelSearchHistory object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null) {
            final BaseRealm otherRealm = ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm();
            if (otherRealm.threadId != realm.threadId) {
                throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
            }
            if (otherRealm.getPath().equals(realm.getPath())) {
                return object;
            }
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (notq.dccast.model.ModelSearchHistory) cachedRealmObject;
        }

        notq.dccast.model.ModelSearchHistory realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(notq.dccast.model.ModelSearchHistory.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = table.findFirstLong(pkColumnIndex, ((ModelSearchHistoryRealmProxyInterface) object).realmGet$id());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(notq.dccast.model.ModelSearchHistory.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.ModelSearchHistoryRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static notq.dccast.model.ModelSearchHistory copy(Realm realm, notq.dccast.model.ModelSearchHistory newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (notq.dccast.model.ModelSearchHistory) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        notq.dccast.model.ModelSearchHistory realmObject = realm.createObjectInternal(notq.dccast.model.ModelSearchHistory.class, ((ModelSearchHistoryRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        ModelSearchHistoryRealmProxyInterface realmObjectSource = (ModelSearchHistoryRealmProxyInterface) newObject;
        ModelSearchHistoryRealmProxyInterface realmObjectCopy = (ModelSearchHistoryRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$keyword(realmObjectSource.realmGet$keyword());
        return realmObject;
    }

    public static long insert(Realm realm, notq.dccast.model.ModelSearchHistory object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(notq.dccast.model.ModelSearchHistory.class);
        long tableNativePtr = table.getNativePtr();
        ModelSearchHistoryColumnInfo columnInfo = (ModelSearchHistoryColumnInfo) realm.getSchema().getColumnInfo(notq.dccast.model.ModelSearchHistory.class);
        long pkColumnIndex = table.getPrimaryKey();
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((ModelSearchHistoryRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((ModelSearchHistoryRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, ((ModelSearchHistoryRealmProxyInterface) object).realmGet$id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$keyword = ((ModelSearchHistoryRealmProxyInterface) object).realmGet$keyword();
        if (realmGet$keyword != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.keywordIndex, rowIndex, realmGet$keyword, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(notq.dccast.model.ModelSearchHistory.class);
        long tableNativePtr = table.getNativePtr();
        ModelSearchHistoryColumnInfo columnInfo = (ModelSearchHistoryColumnInfo) realm.getSchema().getColumnInfo(notq.dccast.model.ModelSearchHistory.class);
        long pkColumnIndex = table.getPrimaryKey();
        notq.dccast.model.ModelSearchHistory object = null;
        while (objects.hasNext()) {
            object = (notq.dccast.model.ModelSearchHistory) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((ModelSearchHistoryRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((ModelSearchHistoryRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, ((ModelSearchHistoryRealmProxyInterface) object).realmGet$id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$keyword = ((ModelSearchHistoryRealmProxyInterface) object).realmGet$keyword();
            if (realmGet$keyword != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.keywordIndex, rowIndex, realmGet$keyword, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, notq.dccast.model.ModelSearchHistory object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(notq.dccast.model.ModelSearchHistory.class);
        long tableNativePtr = table.getNativePtr();
        ModelSearchHistoryColumnInfo columnInfo = (ModelSearchHistoryColumnInfo) realm.getSchema().getColumnInfo(notq.dccast.model.ModelSearchHistory.class);
        long pkColumnIndex = table.getPrimaryKey();
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((ModelSearchHistoryRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((ModelSearchHistoryRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, ((ModelSearchHistoryRealmProxyInterface) object).realmGet$id());
        }
        cache.put(object, rowIndex);
        String realmGet$keyword = ((ModelSearchHistoryRealmProxyInterface) object).realmGet$keyword();
        if (realmGet$keyword != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.keywordIndex, rowIndex, realmGet$keyword, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.keywordIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(notq.dccast.model.ModelSearchHistory.class);
        long tableNativePtr = table.getNativePtr();
        ModelSearchHistoryColumnInfo columnInfo = (ModelSearchHistoryColumnInfo) realm.getSchema().getColumnInfo(notq.dccast.model.ModelSearchHistory.class);
        long pkColumnIndex = table.getPrimaryKey();
        notq.dccast.model.ModelSearchHistory object = null;
        while (objects.hasNext()) {
            object = (notq.dccast.model.ModelSearchHistory) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((ModelSearchHistoryRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((ModelSearchHistoryRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, ((ModelSearchHistoryRealmProxyInterface) object).realmGet$id());
            }
            cache.put(object, rowIndex);
            String realmGet$keyword = ((ModelSearchHistoryRealmProxyInterface) object).realmGet$keyword();
            if (realmGet$keyword != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.keywordIndex, rowIndex, realmGet$keyword, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.keywordIndex, rowIndex, false);
            }
        }
    }

    public static notq.dccast.model.ModelSearchHistory createDetachedCopy(notq.dccast.model.ModelSearchHistory realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        notq.dccast.model.ModelSearchHistory unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new notq.dccast.model.ModelSearchHistory();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (notq.dccast.model.ModelSearchHistory) cachedObject.object;
            }
            unmanagedObject = (notq.dccast.model.ModelSearchHistory) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        ModelSearchHistoryRealmProxyInterface unmanagedCopy = (ModelSearchHistoryRealmProxyInterface) unmanagedObject;
        ModelSearchHistoryRealmProxyInterface realmSource = (ModelSearchHistoryRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());
        unmanagedCopy.realmSet$keyword(realmSource.realmGet$keyword());
        return unmanagedObject;
    }

    static notq.dccast.model.ModelSearchHistory update(Realm realm, notq.dccast.model.ModelSearchHistory realmObject, notq.dccast.model.ModelSearchHistory newObject, Map<RealmModel, RealmObjectProxy> cache) {
        ModelSearchHistoryRealmProxyInterface realmObjectTarget = (ModelSearchHistoryRealmProxyInterface) realmObject;
        ModelSearchHistoryRealmProxyInterface realmObjectSource = (ModelSearchHistoryRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$keyword(realmObjectSource.realmGet$keyword());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("ModelSearchHistory = proxy[");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{keyword:");
        stringBuilder.append(realmGet$keyword() != null ? realmGet$keyword() : "null");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelSearchHistoryRealmProxy aModelSearchHistory = (ModelSearchHistoryRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aModelSearchHistory.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aModelSearchHistory.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aModelSearchHistory.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
