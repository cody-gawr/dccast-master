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
public class ModelChildUserRealmProxy extends notq.dccast.model.user.ModelChildUser
    implements RealmObjectProxy, ModelChildUserRealmProxyInterface {

    static final class ModelChildUserColumnInfo extends ColumnInfo {
        long idIndex;
        long emailIndex;
        long usernameIndex;
        long isSuperuserIndex;
        long isStaffIndex;
        long lastLoginIndex;
        long dateJoinedIndex;
        long firstNameIndex;
        long lastNameIndex;

        ModelChildUserColumnInfo(OsSchemaInfo schemaInfo) {
            super(9);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("ModelChildUser");
            this.idIndex = addColumnDetails("id", objectSchemaInfo);
            this.emailIndex = addColumnDetails("email", objectSchemaInfo);
            this.usernameIndex = addColumnDetails("username", objectSchemaInfo);
            this.isSuperuserIndex = addColumnDetails("isSuperuser", objectSchemaInfo);
            this.isStaffIndex = addColumnDetails("isStaff", objectSchemaInfo);
            this.lastLoginIndex = addColumnDetails("lastLogin", objectSchemaInfo);
            this.dateJoinedIndex = addColumnDetails("dateJoined", objectSchemaInfo);
            this.firstNameIndex = addColumnDetails("firstName", objectSchemaInfo);
            this.lastNameIndex = addColumnDetails("lastName", objectSchemaInfo);
        }

        ModelChildUserColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new ModelChildUserColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final ModelChildUserColumnInfo src = (ModelChildUserColumnInfo) rawSrc;
            final ModelChildUserColumnInfo dst = (ModelChildUserColumnInfo) rawDst;
            dst.idIndex = src.idIndex;
            dst.emailIndex = src.emailIndex;
            dst.usernameIndex = src.usernameIndex;
            dst.isSuperuserIndex = src.isSuperuserIndex;
            dst.isStaffIndex = src.isStaffIndex;
            dst.lastLoginIndex = src.lastLoginIndex;
            dst.dateJoinedIndex = src.dateJoinedIndex;
            dst.firstNameIndex = src.firstNameIndex;
            dst.lastNameIndex = src.lastNameIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("id");
        fieldNames.add("email");
        fieldNames.add("username");
        fieldNames.add("isSuperuser");
        fieldNames.add("isStaff");
        fieldNames.add("lastLogin");
        fieldNames.add("dateJoined");
        fieldNames.add("firstName");
        fieldNames.add("lastName");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private ModelChildUserColumnInfo columnInfo;
    private ProxyState<notq.dccast.model.user.ModelChildUser> proxyState;

    ModelChildUserRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (ModelChildUserColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<notq.dccast.model.user.ModelChildUser>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public Integer realmGet$id() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.idIndex)) {
            return null;
        }
        return (int) proxyState.getRow$realm().getLong(columnInfo.idIndex);
    }

    @Override
    public void realmSet$id(Integer value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'id' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$email() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.emailIndex);
    }

    @Override
    public void realmSet$email(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.emailIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.emailIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.emailIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.emailIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$username() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.usernameIndex);
    }

    @Override
    public void realmSet$username(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.usernameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.usernameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.usernameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.usernameIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Boolean realmGet$isSuperuser() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.isSuperuserIndex)) {
            return null;
        }
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.isSuperuserIndex);
    }

    @Override
    public void realmSet$isSuperuser(Boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.isSuperuserIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBoolean(columnInfo.isSuperuserIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.isSuperuserIndex);
            return;
        }
        proxyState.getRow$realm().setBoolean(columnInfo.isSuperuserIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Boolean realmGet$isStaff() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.isStaffIndex)) {
            return null;
        }
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.isStaffIndex);
    }

    @Override
    public void realmSet$isStaff(Boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.isStaffIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBoolean(columnInfo.isStaffIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.isStaffIndex);
            return;
        }
        proxyState.getRow$realm().setBoolean(columnInfo.isStaffIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$lastLogin() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.lastLoginIndex);
    }

    @Override
    public void realmSet$lastLogin(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.lastLoginIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.lastLoginIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.lastLoginIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.lastLoginIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$dateJoined() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.dateJoinedIndex);
    }

    @Override
    public void realmSet$dateJoined(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.dateJoinedIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.dateJoinedIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.dateJoinedIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.dateJoinedIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$firstName() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.firstNameIndex);
    }

    @Override
    public void realmSet$firstName(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.firstNameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.firstNameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.firstNameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.firstNameIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$lastName() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.lastNameIndex);
    }

    @Override
    public void realmSet$lastName(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.lastNameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.lastNameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.lastNameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.lastNameIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("ModelChildUser");
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("email", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("username", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("isSuperuser", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("isStaff", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("lastLogin", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("dateJoined", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("firstName", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("lastName", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static ModelChildUserColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new ModelChildUserColumnInfo(schemaInfo);
    }

    public static String getTableName() {
        return "class_ModelChildUser";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static notq.dccast.model.user.ModelChildUser createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        notq.dccast.model.user.ModelChildUser obj = null;
        if (update) {
            Table table = realm.getTable(notq.dccast.model.user.ModelChildUser.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = Table.NO_MATCH;
            if (json.isNull("id")) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(notq.dccast.model.user.ModelChildUser.class), false, Collections.<String> emptyList());
                    obj = new io.realm.ModelChildUserRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.ModelChildUserRealmProxy) realm.createObjectInternal(notq.dccast.model.user.ModelChildUser.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.ModelChildUserRealmProxy) realm.createObjectInternal(notq.dccast.model.user.ModelChildUser.class, json.getInt("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }

        final ModelChildUserRealmProxyInterface objProxy = (ModelChildUserRealmProxyInterface) obj;
        if (json.has("email")) {
            if (json.isNull("email")) {
                objProxy.realmSet$email(null);
            } else {
                objProxy.realmSet$email((String) json.getString("email"));
            }
        }
        if (json.has("username")) {
            if (json.isNull("username")) {
                objProxy.realmSet$username(null);
            } else {
                objProxy.realmSet$username((String) json.getString("username"));
            }
        }
        if (json.has("isSuperuser")) {
            if (json.isNull("isSuperuser")) {
                objProxy.realmSet$isSuperuser(null);
            } else {
                objProxy.realmSet$isSuperuser((boolean) json.getBoolean("isSuperuser"));
            }
        }
        if (json.has("isStaff")) {
            if (json.isNull("isStaff")) {
                objProxy.realmSet$isStaff(null);
            } else {
                objProxy.realmSet$isStaff((boolean) json.getBoolean("isStaff"));
            }
        }
        if (json.has("lastLogin")) {
            if (json.isNull("lastLogin")) {
                objProxy.realmSet$lastLogin(null);
            } else {
                objProxy.realmSet$lastLogin((String) json.getString("lastLogin"));
            }
        }
        if (json.has("dateJoined")) {
            if (json.isNull("dateJoined")) {
                objProxy.realmSet$dateJoined(null);
            } else {
                objProxy.realmSet$dateJoined((String) json.getString("dateJoined"));
            }
        }
        if (json.has("firstName")) {
            if (json.isNull("firstName")) {
                objProxy.realmSet$firstName(null);
            } else {
                objProxy.realmSet$firstName((String) json.getString("firstName"));
            }
        }
        if (json.has("lastName")) {
            if (json.isNull("lastName")) {
                objProxy.realmSet$lastName(null);
            } else {
                objProxy.realmSet$lastName((String) json.getString("lastName"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static notq.dccast.model.user.ModelChildUser createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final notq.dccast.model.user.ModelChildUser obj = new notq.dccast.model.user.ModelChildUser();
        final ModelChildUserRealmProxyInterface objProxy = (ModelChildUserRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$id(null);
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("email")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$email((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$email(null);
                }
            } else if (name.equals("username")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$username((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$username(null);
                }
            } else if (name.equals("isSuperuser")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$isSuperuser((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$isSuperuser(null);
                }
            } else if (name.equals("isStaff")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$isStaff((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$isStaff(null);
                }
            } else if (name.equals("lastLogin")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$lastLogin((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$lastLogin(null);
                }
            } else if (name.equals("dateJoined")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$dateJoined((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$dateJoined(null);
                }
            } else if (name.equals("firstName")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$firstName((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$firstName(null);
                }
            } else if (name.equals("lastName")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$lastName((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$lastName(null);
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

    public static notq.dccast.model.user.ModelChildUser copyOrUpdate(Realm realm, notq.dccast.model.user.ModelChildUser object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (notq.dccast.model.user.ModelChildUser) cachedRealmObject;
        }

        notq.dccast.model.user.ModelChildUser realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(notq.dccast.model.user.ModelChildUser.class);
            long pkColumnIndex = table.getPrimaryKey();
            Number value = ((ModelChildUserRealmProxyInterface) object).realmGet$id();
            long rowIndex = Table.NO_MATCH;
            if (value == null) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstLong(pkColumnIndex, value.longValue());
            }
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(notq.dccast.model.user.ModelChildUser.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.ModelChildUserRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static notq.dccast.model.user.ModelChildUser copy(Realm realm, notq.dccast.model.user.ModelChildUser newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (notq.dccast.model.user.ModelChildUser) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        notq.dccast.model.user.ModelChildUser realmObject = realm.createObjectInternal(notq.dccast.model.user.ModelChildUser.class, ((ModelChildUserRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        ModelChildUserRealmProxyInterface realmObjectSource = (ModelChildUserRealmProxyInterface) newObject;
        ModelChildUserRealmProxyInterface realmObjectCopy = (ModelChildUserRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$email(realmObjectSource.realmGet$email());
        realmObjectCopy.realmSet$username(realmObjectSource.realmGet$username());
        realmObjectCopy.realmSet$isSuperuser(realmObjectSource.realmGet$isSuperuser());
        realmObjectCopy.realmSet$isStaff(realmObjectSource.realmGet$isStaff());
        realmObjectCopy.realmSet$lastLogin(realmObjectSource.realmGet$lastLogin());
        realmObjectCopy.realmSet$dateJoined(realmObjectSource.realmGet$dateJoined());
        realmObjectCopy.realmSet$firstName(realmObjectSource.realmGet$firstName());
        realmObjectCopy.realmSet$lastName(realmObjectSource.realmGet$lastName());
        return realmObject;
    }

    public static long insert(Realm realm, notq.dccast.model.user.ModelChildUser object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(notq.dccast.model.user.ModelChildUser.class);
        long tableNativePtr = table.getNativePtr();
        ModelChildUserColumnInfo columnInfo = (ModelChildUserColumnInfo) realm.getSchema().getColumnInfo(notq.dccast.model.user.ModelChildUser.class);
        long pkColumnIndex = table.getPrimaryKey();
        Object primaryKeyValue = ((ModelChildUserRealmProxyInterface) object).realmGet$id();
        long rowIndex = Table.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((ModelChildUserRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, ((ModelChildUserRealmProxyInterface) object).realmGet$id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$email = ((ModelChildUserRealmProxyInterface) object).realmGet$email();
        if (realmGet$email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.emailIndex, rowIndex, realmGet$email, false);
        }
        String realmGet$username = ((ModelChildUserRealmProxyInterface) object).realmGet$username();
        if (realmGet$username != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.usernameIndex, rowIndex, realmGet$username, false);
        }
        Boolean realmGet$isSuperuser = ((ModelChildUserRealmProxyInterface) object).realmGet$isSuperuser();
        if (realmGet$isSuperuser != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.isSuperuserIndex, rowIndex, realmGet$isSuperuser, false);
        }
        Boolean realmGet$isStaff = ((ModelChildUserRealmProxyInterface) object).realmGet$isStaff();
        if (realmGet$isStaff != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.isStaffIndex, rowIndex, realmGet$isStaff, false);
        }
        String realmGet$lastLogin = ((ModelChildUserRealmProxyInterface) object).realmGet$lastLogin();
        if (realmGet$lastLogin != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.lastLoginIndex, rowIndex, realmGet$lastLogin, false);
        }
        String realmGet$dateJoined = ((ModelChildUserRealmProxyInterface) object).realmGet$dateJoined();
        if (realmGet$dateJoined != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dateJoinedIndex, rowIndex, realmGet$dateJoined, false);
        }
        String realmGet$firstName = ((ModelChildUserRealmProxyInterface) object).realmGet$firstName();
        if (realmGet$firstName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.firstNameIndex, rowIndex, realmGet$firstName, false);
        }
        String realmGet$lastName = ((ModelChildUserRealmProxyInterface) object).realmGet$lastName();
        if (realmGet$lastName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.lastNameIndex, rowIndex, realmGet$lastName, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(notq.dccast.model.user.ModelChildUser.class);
        long tableNativePtr = table.getNativePtr();
        ModelChildUserColumnInfo columnInfo = (ModelChildUserColumnInfo) realm.getSchema().getColumnInfo(notq.dccast.model.user.ModelChildUser.class);
        long pkColumnIndex = table.getPrimaryKey();
        notq.dccast.model.user.ModelChildUser object = null;
        while (objects.hasNext()) {
            object = (notq.dccast.model.user.ModelChildUser) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            Object primaryKeyValue = ((ModelChildUserRealmProxyInterface) object).realmGet$id();
            long rowIndex = Table.NO_MATCH;
            if (primaryKeyValue == null) {
                rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
            } else {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((ModelChildUserRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, ((ModelChildUserRealmProxyInterface) object).realmGet$id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$email = ((ModelChildUserRealmProxyInterface) object).realmGet$email();
            if (realmGet$email != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.emailIndex, rowIndex, realmGet$email, false);
            }
            String realmGet$username = ((ModelChildUserRealmProxyInterface) object).realmGet$username();
            if (realmGet$username != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.usernameIndex, rowIndex, realmGet$username, false);
            }
            Boolean realmGet$isSuperuser = ((ModelChildUserRealmProxyInterface) object).realmGet$isSuperuser();
            if (realmGet$isSuperuser != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.isSuperuserIndex, rowIndex, realmGet$isSuperuser, false);
            }
            Boolean realmGet$isStaff = ((ModelChildUserRealmProxyInterface) object).realmGet$isStaff();
            if (realmGet$isStaff != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.isStaffIndex, rowIndex, realmGet$isStaff, false);
            }
            String realmGet$lastLogin = ((ModelChildUserRealmProxyInterface) object).realmGet$lastLogin();
            if (realmGet$lastLogin != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.lastLoginIndex, rowIndex, realmGet$lastLogin, false);
            }
            String realmGet$dateJoined = ((ModelChildUserRealmProxyInterface) object).realmGet$dateJoined();
            if (realmGet$dateJoined != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dateJoinedIndex, rowIndex, realmGet$dateJoined, false);
            }
            String realmGet$firstName = ((ModelChildUserRealmProxyInterface) object).realmGet$firstName();
            if (realmGet$firstName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.firstNameIndex, rowIndex, realmGet$firstName, false);
            }
            String realmGet$lastName = ((ModelChildUserRealmProxyInterface) object).realmGet$lastName();
            if (realmGet$lastName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.lastNameIndex, rowIndex, realmGet$lastName, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, notq.dccast.model.user.ModelChildUser object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(notq.dccast.model.user.ModelChildUser.class);
        long tableNativePtr = table.getNativePtr();
        ModelChildUserColumnInfo columnInfo = (ModelChildUserColumnInfo) realm.getSchema().getColumnInfo(notq.dccast.model.user.ModelChildUser.class);
        long pkColumnIndex = table.getPrimaryKey();
        Object primaryKeyValue = ((ModelChildUserRealmProxyInterface) object).realmGet$id();
        long rowIndex = Table.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((ModelChildUserRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, ((ModelChildUserRealmProxyInterface) object).realmGet$id());
        }
        cache.put(object, rowIndex);
        String realmGet$email = ((ModelChildUserRealmProxyInterface) object).realmGet$email();
        if (realmGet$email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.emailIndex, rowIndex, realmGet$email, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.emailIndex, rowIndex, false);
        }
        String realmGet$username = ((ModelChildUserRealmProxyInterface) object).realmGet$username();
        if (realmGet$username != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.usernameIndex, rowIndex, realmGet$username, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.usernameIndex, rowIndex, false);
        }
        Boolean realmGet$isSuperuser = ((ModelChildUserRealmProxyInterface) object).realmGet$isSuperuser();
        if (realmGet$isSuperuser != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.isSuperuserIndex, rowIndex, realmGet$isSuperuser, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.isSuperuserIndex, rowIndex, false);
        }
        Boolean realmGet$isStaff = ((ModelChildUserRealmProxyInterface) object).realmGet$isStaff();
        if (realmGet$isStaff != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.isStaffIndex, rowIndex, realmGet$isStaff, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.isStaffIndex, rowIndex, false);
        }
        String realmGet$lastLogin = ((ModelChildUserRealmProxyInterface) object).realmGet$lastLogin();
        if (realmGet$lastLogin != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.lastLoginIndex, rowIndex, realmGet$lastLogin, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.lastLoginIndex, rowIndex, false);
        }
        String realmGet$dateJoined = ((ModelChildUserRealmProxyInterface) object).realmGet$dateJoined();
        if (realmGet$dateJoined != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dateJoinedIndex, rowIndex, realmGet$dateJoined, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dateJoinedIndex, rowIndex, false);
        }
        String realmGet$firstName = ((ModelChildUserRealmProxyInterface) object).realmGet$firstName();
        if (realmGet$firstName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.firstNameIndex, rowIndex, realmGet$firstName, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.firstNameIndex, rowIndex, false);
        }
        String realmGet$lastName = ((ModelChildUserRealmProxyInterface) object).realmGet$lastName();
        if (realmGet$lastName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.lastNameIndex, rowIndex, realmGet$lastName, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.lastNameIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(notq.dccast.model.user.ModelChildUser.class);
        long tableNativePtr = table.getNativePtr();
        ModelChildUserColumnInfo columnInfo = (ModelChildUserColumnInfo) realm.getSchema().getColumnInfo(notq.dccast.model.user.ModelChildUser.class);
        long pkColumnIndex = table.getPrimaryKey();
        notq.dccast.model.user.ModelChildUser object = null;
        while (objects.hasNext()) {
            object = (notq.dccast.model.user.ModelChildUser) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            Object primaryKeyValue = ((ModelChildUserRealmProxyInterface) object).realmGet$id();
            long rowIndex = Table.NO_MATCH;
            if (primaryKeyValue == null) {
                rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
            } else {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((ModelChildUserRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, ((ModelChildUserRealmProxyInterface) object).realmGet$id());
            }
            cache.put(object, rowIndex);
            String realmGet$email = ((ModelChildUserRealmProxyInterface) object).realmGet$email();
            if (realmGet$email != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.emailIndex, rowIndex, realmGet$email, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.emailIndex, rowIndex, false);
            }
            String realmGet$username = ((ModelChildUserRealmProxyInterface) object).realmGet$username();
            if (realmGet$username != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.usernameIndex, rowIndex, realmGet$username, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.usernameIndex, rowIndex, false);
            }
            Boolean realmGet$isSuperuser = ((ModelChildUserRealmProxyInterface) object).realmGet$isSuperuser();
            if (realmGet$isSuperuser != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.isSuperuserIndex, rowIndex, realmGet$isSuperuser, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.isSuperuserIndex, rowIndex, false);
            }
            Boolean realmGet$isStaff = ((ModelChildUserRealmProxyInterface) object).realmGet$isStaff();
            if (realmGet$isStaff != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.isStaffIndex, rowIndex, realmGet$isStaff, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.isStaffIndex, rowIndex, false);
            }
            String realmGet$lastLogin = ((ModelChildUserRealmProxyInterface) object).realmGet$lastLogin();
            if (realmGet$lastLogin != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.lastLoginIndex, rowIndex, realmGet$lastLogin, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.lastLoginIndex, rowIndex, false);
            }
            String realmGet$dateJoined = ((ModelChildUserRealmProxyInterface) object).realmGet$dateJoined();
            if (realmGet$dateJoined != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dateJoinedIndex, rowIndex, realmGet$dateJoined, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dateJoinedIndex, rowIndex, false);
            }
            String realmGet$firstName = ((ModelChildUserRealmProxyInterface) object).realmGet$firstName();
            if (realmGet$firstName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.firstNameIndex, rowIndex, realmGet$firstName, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.firstNameIndex, rowIndex, false);
            }
            String realmGet$lastName = ((ModelChildUserRealmProxyInterface) object).realmGet$lastName();
            if (realmGet$lastName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.lastNameIndex, rowIndex, realmGet$lastName, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.lastNameIndex, rowIndex, false);
            }
        }
    }

    public static notq.dccast.model.user.ModelChildUser createDetachedCopy(notq.dccast.model.user.ModelChildUser realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        notq.dccast.model.user.ModelChildUser unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new notq.dccast.model.user.ModelChildUser();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (notq.dccast.model.user.ModelChildUser) cachedObject.object;
            }
            unmanagedObject = (notq.dccast.model.user.ModelChildUser) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        ModelChildUserRealmProxyInterface unmanagedCopy = (ModelChildUserRealmProxyInterface) unmanagedObject;
        ModelChildUserRealmProxyInterface realmSource = (ModelChildUserRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());
        unmanagedCopy.realmSet$email(realmSource.realmGet$email());
        unmanagedCopy.realmSet$username(realmSource.realmGet$username());
        unmanagedCopy.realmSet$isSuperuser(realmSource.realmGet$isSuperuser());
        unmanagedCopy.realmSet$isStaff(realmSource.realmGet$isStaff());
        unmanagedCopy.realmSet$lastLogin(realmSource.realmGet$lastLogin());
        unmanagedCopy.realmSet$dateJoined(realmSource.realmGet$dateJoined());
        unmanagedCopy.realmSet$firstName(realmSource.realmGet$firstName());
        unmanagedCopy.realmSet$lastName(realmSource.realmGet$lastName());
        return unmanagedObject;
    }

    static notq.dccast.model.user.ModelChildUser update(Realm realm, notq.dccast.model.user.ModelChildUser realmObject, notq.dccast.model.user.ModelChildUser newObject, Map<RealmModel, RealmObjectProxy> cache) {
        ModelChildUserRealmProxyInterface realmObjectTarget = (ModelChildUserRealmProxyInterface) realmObject;
        ModelChildUserRealmProxyInterface realmObjectSource = (ModelChildUserRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$email(realmObjectSource.realmGet$email());
        realmObjectTarget.realmSet$username(realmObjectSource.realmGet$username());
        realmObjectTarget.realmSet$isSuperuser(realmObjectSource.realmGet$isSuperuser());
        realmObjectTarget.realmSet$isStaff(realmObjectSource.realmGet$isStaff());
        realmObjectTarget.realmSet$lastLogin(realmObjectSource.realmGet$lastLogin());
        realmObjectTarget.realmSet$dateJoined(realmObjectSource.realmGet$dateJoined());
        realmObjectTarget.realmSet$firstName(realmObjectSource.realmGet$firstName());
        realmObjectTarget.realmSet$lastName(realmObjectSource.realmGet$lastName());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("ModelChildUser = proxy[");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id() != null ? realmGet$id() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{email:");
        stringBuilder.append(realmGet$email() != null ? realmGet$email() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{username:");
        stringBuilder.append(realmGet$username() != null ? realmGet$username() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{isSuperuser:");
        stringBuilder.append(realmGet$isSuperuser() != null ? realmGet$isSuperuser() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{isStaff:");
        stringBuilder.append(realmGet$isStaff() != null ? realmGet$isStaff() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{lastLogin:");
        stringBuilder.append(realmGet$lastLogin() != null ? realmGet$lastLogin() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{dateJoined:");
        stringBuilder.append(realmGet$dateJoined() != null ? realmGet$dateJoined() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{firstName:");
        stringBuilder.append(realmGet$firstName() != null ? realmGet$firstName() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{lastName:");
        stringBuilder.append(realmGet$lastName() != null ? realmGet$lastName() : "null");
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
        ModelChildUserRealmProxy aModelChildUser = (ModelChildUserRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aModelChildUser.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aModelChildUser.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aModelChildUser.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
