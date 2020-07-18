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
public class ModelUserRealmProxy extends notq.dccast.model.user.ModelUser
    implements RealmObjectProxy, ModelUserRealmProxyInterface {

    static final class ModelUserColumnInfo extends ColumnInfo {
        long idIndex;
        long userIndex;
        long userNoIndex;
        long nameCertificationIndex;
        long autoLoginIndex;
        long nickNameIndex;
        long stateMessageIndex;
        long noticeLiveIndex;
        long noticeVodIndex;
        long noticeChatIndex;
        long noticeNoticeIndex;
        long noticeSoundIndex;
        long noticeVibrationIndex;
        long phoneIndex;
        long phoneCertificationIndex;
        long limitMobileDataIndex;
        long stopRecentViewIndex;
        long stopRecentSearchIndex;
        long profileImageIndex;
        long phoneCertificationDateIndex;
        long adultCertificationIndex;
        long setPassIndex;
        long passStringIndex;
        long adultCertificationDateIndex;
        long lastNameCertificationIndex;
        long emailCertificationIndex;
        long isVipIndex;
        long isVipActiveIndex;
        long vipCreateDateIndex;
        long profileOriginalImageIndex;
        long clientTokenIndex;
        long onAirIndex;
        long tokenIndex;

        ModelUserColumnInfo(OsSchemaInfo schemaInfo) {
            super(33);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("ModelUser");
            this.idIndex = addColumnDetails("id", objectSchemaInfo);
            this.userIndex = addColumnDetails("user", objectSchemaInfo);
            this.userNoIndex = addColumnDetails("userNo", objectSchemaInfo);
            this.nameCertificationIndex = addColumnDetails("nameCertification", objectSchemaInfo);
            this.autoLoginIndex = addColumnDetails("autoLogin", objectSchemaInfo);
            this.nickNameIndex = addColumnDetails("nickName", objectSchemaInfo);
            this.stateMessageIndex = addColumnDetails("stateMessage", objectSchemaInfo);
            this.noticeLiveIndex = addColumnDetails("noticeLive", objectSchemaInfo);
            this.noticeVodIndex = addColumnDetails("noticeVod", objectSchemaInfo);
            this.noticeChatIndex = addColumnDetails("noticeChat", objectSchemaInfo);
            this.noticeNoticeIndex = addColumnDetails("noticeNotice", objectSchemaInfo);
            this.noticeSoundIndex = addColumnDetails("noticeSound", objectSchemaInfo);
            this.noticeVibrationIndex = addColumnDetails("noticeVibration", objectSchemaInfo);
            this.phoneIndex = addColumnDetails("phone", objectSchemaInfo);
            this.phoneCertificationIndex = addColumnDetails("phoneCertification", objectSchemaInfo);
            this.limitMobileDataIndex = addColumnDetails("limitMobileData", objectSchemaInfo);
            this.stopRecentViewIndex = addColumnDetails("stopRecentView", objectSchemaInfo);
            this.stopRecentSearchIndex = addColumnDetails("stopRecentSearch", objectSchemaInfo);
            this.profileImageIndex = addColumnDetails("profileImage", objectSchemaInfo);
            this.phoneCertificationDateIndex = addColumnDetails("phoneCertificationDate", objectSchemaInfo);
            this.adultCertificationIndex = addColumnDetails("adultCertification", objectSchemaInfo);
            this.setPassIndex = addColumnDetails("setPass", objectSchemaInfo);
            this.passStringIndex = addColumnDetails("passString", objectSchemaInfo);
            this.adultCertificationDateIndex = addColumnDetails("adultCertificationDate", objectSchemaInfo);
            this.lastNameCertificationIndex = addColumnDetails("lastNameCertification", objectSchemaInfo);
            this.emailCertificationIndex = addColumnDetails("emailCertification", objectSchemaInfo);
            this.isVipIndex = addColumnDetails("isVip", objectSchemaInfo);
            this.isVipActiveIndex = addColumnDetails("isVipActive", objectSchemaInfo);
            this.vipCreateDateIndex = addColumnDetails("vipCreateDate", objectSchemaInfo);
            this.profileOriginalImageIndex = addColumnDetails("profileOriginalImage", objectSchemaInfo);
            this.clientTokenIndex = addColumnDetails("clientToken", objectSchemaInfo);
            this.onAirIndex = addColumnDetails("onAir", objectSchemaInfo);
            this.tokenIndex = addColumnDetails("token", objectSchemaInfo);
        }

        ModelUserColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new ModelUserColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final ModelUserColumnInfo src = (ModelUserColumnInfo) rawSrc;
            final ModelUserColumnInfo dst = (ModelUserColumnInfo) rawDst;
            dst.idIndex = src.idIndex;
            dst.userIndex = src.userIndex;
            dst.userNoIndex = src.userNoIndex;
            dst.nameCertificationIndex = src.nameCertificationIndex;
            dst.autoLoginIndex = src.autoLoginIndex;
            dst.nickNameIndex = src.nickNameIndex;
            dst.stateMessageIndex = src.stateMessageIndex;
            dst.noticeLiveIndex = src.noticeLiveIndex;
            dst.noticeVodIndex = src.noticeVodIndex;
            dst.noticeChatIndex = src.noticeChatIndex;
            dst.noticeNoticeIndex = src.noticeNoticeIndex;
            dst.noticeSoundIndex = src.noticeSoundIndex;
            dst.noticeVibrationIndex = src.noticeVibrationIndex;
            dst.phoneIndex = src.phoneIndex;
            dst.phoneCertificationIndex = src.phoneCertificationIndex;
            dst.limitMobileDataIndex = src.limitMobileDataIndex;
            dst.stopRecentViewIndex = src.stopRecentViewIndex;
            dst.stopRecentSearchIndex = src.stopRecentSearchIndex;
            dst.profileImageIndex = src.profileImageIndex;
            dst.phoneCertificationDateIndex = src.phoneCertificationDateIndex;
            dst.adultCertificationIndex = src.adultCertificationIndex;
            dst.setPassIndex = src.setPassIndex;
            dst.passStringIndex = src.passStringIndex;
            dst.adultCertificationDateIndex = src.adultCertificationDateIndex;
            dst.lastNameCertificationIndex = src.lastNameCertificationIndex;
            dst.emailCertificationIndex = src.emailCertificationIndex;
            dst.isVipIndex = src.isVipIndex;
            dst.isVipActiveIndex = src.isVipActiveIndex;
            dst.vipCreateDateIndex = src.vipCreateDateIndex;
            dst.profileOriginalImageIndex = src.profileOriginalImageIndex;
            dst.clientTokenIndex = src.clientTokenIndex;
            dst.onAirIndex = src.onAirIndex;
            dst.tokenIndex = src.tokenIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("id");
        fieldNames.add("user");
        fieldNames.add("userNo");
        fieldNames.add("nameCertification");
        fieldNames.add("autoLogin");
        fieldNames.add("nickName");
        fieldNames.add("stateMessage");
        fieldNames.add("noticeLive");
        fieldNames.add("noticeVod");
        fieldNames.add("noticeChat");
        fieldNames.add("noticeNotice");
        fieldNames.add("noticeSound");
        fieldNames.add("noticeVibration");
        fieldNames.add("phone");
        fieldNames.add("phoneCertification");
        fieldNames.add("limitMobileData");
        fieldNames.add("stopRecentView");
        fieldNames.add("stopRecentSearch");
        fieldNames.add("profileImage");
        fieldNames.add("phoneCertificationDate");
        fieldNames.add("adultCertification");
        fieldNames.add("setPass");
        fieldNames.add("passString");
        fieldNames.add("adultCertificationDate");
        fieldNames.add("lastNameCertification");
        fieldNames.add("emailCertification");
        fieldNames.add("isVip");
        fieldNames.add("isVipActive");
        fieldNames.add("vipCreateDate");
        fieldNames.add("profileOriginalImage");
        fieldNames.add("clientToken");
        fieldNames.add("onAir");
        fieldNames.add("token");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private ModelUserColumnInfo columnInfo;
    private ProxyState<notq.dccast.model.user.ModelUser> proxyState;

    ModelUserRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (ModelUserColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<notq.dccast.model.user.ModelUser>(this);
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
    public notq.dccast.model.user.ModelChildUser realmGet$user() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.userIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(notq.dccast.model.user.ModelChildUser.class, proxyState.getRow$realm().getLink(columnInfo.userIndex), false, Collections.<String>emptyList());
    }

    @Override
    public void realmSet$user(notq.dccast.model.user.ModelChildUser value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("user")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.userIndex);
                return;
            }
            if (!RealmObject.isValid(value)) {
                throw new IllegalArgumentException("'value' is not a valid managed object.");
            }
            if (((RealmObjectProxy) value).realmGet$proxyState().getRealm$realm() != proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("'value' belongs to a different Realm.");
            }
            row.getTable().setLink(columnInfo.userIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.userIndex);
            return;
        }
        if (!(RealmObject.isManaged(value) && RealmObject.isValid(value))) {
            throw new IllegalArgumentException("'value' is not a valid managed object.");
        }
        if (((RealmObjectProxy) value).realmGet$proxyState().getRealm$realm() != proxyState.getRealm$realm()) {
            throw new IllegalArgumentException("'value' belongs to a different Realm.");
        }
        proxyState.getRow$realm().setLink(columnInfo.userIndex, ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$userNo() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.userNoIndex);
    }

    @Override
    public void realmSet$userNo(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.userNoIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.userNoIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.userNoIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.userNoIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Boolean realmGet$nameCertification() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.nameCertificationIndex)) {
            return null;
        }
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.nameCertificationIndex);
    }

    @Override
    public void realmSet$nameCertification(Boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.nameCertificationIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBoolean(columnInfo.nameCertificationIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.nameCertificationIndex);
            return;
        }
        proxyState.getRow$realm().setBoolean(columnInfo.nameCertificationIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Boolean realmGet$autoLogin() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.autoLoginIndex)) {
            return null;
        }
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.autoLoginIndex);
    }

    @Override
    public void realmSet$autoLogin(Boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.autoLoginIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBoolean(columnInfo.autoLoginIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.autoLoginIndex);
            return;
        }
        proxyState.getRow$realm().setBoolean(columnInfo.autoLoginIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$nickName() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.nickNameIndex);
    }

    @Override
    public void realmSet$nickName(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.nickNameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.nickNameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.nickNameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.nickNameIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$stateMessage() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.stateMessageIndex);
    }

    @Override
    public void realmSet$stateMessage(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.stateMessageIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.stateMessageIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.stateMessageIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.stateMessageIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Boolean realmGet$noticeLive() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.noticeLiveIndex)) {
            return null;
        }
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.noticeLiveIndex);
    }

    @Override
    public void realmSet$noticeLive(Boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.noticeLiveIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBoolean(columnInfo.noticeLiveIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.noticeLiveIndex);
            return;
        }
        proxyState.getRow$realm().setBoolean(columnInfo.noticeLiveIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Boolean realmGet$noticeVod() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.noticeVodIndex)) {
            return null;
        }
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.noticeVodIndex);
    }

    @Override
    public void realmSet$noticeVod(Boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.noticeVodIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBoolean(columnInfo.noticeVodIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.noticeVodIndex);
            return;
        }
        proxyState.getRow$realm().setBoolean(columnInfo.noticeVodIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Boolean realmGet$noticeChat() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.noticeChatIndex)) {
            return null;
        }
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.noticeChatIndex);
    }

    @Override
    public void realmSet$noticeChat(Boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.noticeChatIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBoolean(columnInfo.noticeChatIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.noticeChatIndex);
            return;
        }
        proxyState.getRow$realm().setBoolean(columnInfo.noticeChatIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Boolean realmGet$noticeNotice() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.noticeNoticeIndex)) {
            return null;
        }
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.noticeNoticeIndex);
    }

    @Override
    public void realmSet$noticeNotice(Boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.noticeNoticeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBoolean(columnInfo.noticeNoticeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.noticeNoticeIndex);
            return;
        }
        proxyState.getRow$realm().setBoolean(columnInfo.noticeNoticeIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Boolean realmGet$noticeSound() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.noticeSoundIndex)) {
            return null;
        }
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.noticeSoundIndex);
    }

    @Override
    public void realmSet$noticeSound(Boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.noticeSoundIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBoolean(columnInfo.noticeSoundIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.noticeSoundIndex);
            return;
        }
        proxyState.getRow$realm().setBoolean(columnInfo.noticeSoundIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Boolean realmGet$noticeVibration() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.noticeVibrationIndex)) {
            return null;
        }
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.noticeVibrationIndex);
    }

    @Override
    public void realmSet$noticeVibration(Boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.noticeVibrationIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBoolean(columnInfo.noticeVibrationIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.noticeVibrationIndex);
            return;
        }
        proxyState.getRow$realm().setBoolean(columnInfo.noticeVibrationIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$phone() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.phoneIndex);
    }

    @Override
    public void realmSet$phone(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.phoneIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.phoneIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.phoneIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.phoneIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Boolean realmGet$phoneCertification() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.phoneCertificationIndex)) {
            return null;
        }
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.phoneCertificationIndex);
    }

    @Override
    public void realmSet$phoneCertification(Boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.phoneCertificationIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBoolean(columnInfo.phoneCertificationIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.phoneCertificationIndex);
            return;
        }
        proxyState.getRow$realm().setBoolean(columnInfo.phoneCertificationIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Boolean realmGet$limitMobileData() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.limitMobileDataIndex)) {
            return null;
        }
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.limitMobileDataIndex);
    }

    @Override
    public void realmSet$limitMobileData(Boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.limitMobileDataIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBoolean(columnInfo.limitMobileDataIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.limitMobileDataIndex);
            return;
        }
        proxyState.getRow$realm().setBoolean(columnInfo.limitMobileDataIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Boolean realmGet$stopRecentView() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.stopRecentViewIndex)) {
            return null;
        }
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.stopRecentViewIndex);
    }

    @Override
    public void realmSet$stopRecentView(Boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.stopRecentViewIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBoolean(columnInfo.stopRecentViewIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.stopRecentViewIndex);
            return;
        }
        proxyState.getRow$realm().setBoolean(columnInfo.stopRecentViewIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Boolean realmGet$stopRecentSearch() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.stopRecentSearchIndex)) {
            return null;
        }
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.stopRecentSearchIndex);
    }

    @Override
    public void realmSet$stopRecentSearch(Boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.stopRecentSearchIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBoolean(columnInfo.stopRecentSearchIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.stopRecentSearchIndex);
            return;
        }
        proxyState.getRow$realm().setBoolean(columnInfo.stopRecentSearchIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$profileImage() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.profileImageIndex);
    }

    @Override
    public void realmSet$profileImage(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.profileImageIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.profileImageIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.profileImageIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.profileImageIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$phoneCertificationDate() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.phoneCertificationDateIndex);
    }

    @Override
    public void realmSet$phoneCertificationDate(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.phoneCertificationDateIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.phoneCertificationDateIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.phoneCertificationDateIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.phoneCertificationDateIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Boolean realmGet$adultCertification() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.adultCertificationIndex)) {
            return null;
        }
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.adultCertificationIndex);
    }

    @Override
    public void realmSet$adultCertification(Boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.adultCertificationIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBoolean(columnInfo.adultCertificationIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.adultCertificationIndex);
            return;
        }
        proxyState.getRow$realm().setBoolean(columnInfo.adultCertificationIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Boolean realmGet$setPass() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.setPassIndex)) {
            return null;
        }
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.setPassIndex);
    }

    @Override
    public void realmSet$setPass(Boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.setPassIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBoolean(columnInfo.setPassIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.setPassIndex);
            return;
        }
        proxyState.getRow$realm().setBoolean(columnInfo.setPassIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$passString() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.passStringIndex);
    }

    @Override
    public void realmSet$passString(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.passStringIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.passStringIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.passStringIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.passStringIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$adultCertificationDate() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.adultCertificationDateIndex);
    }

    @Override
    public void realmSet$adultCertificationDate(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.adultCertificationDateIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.adultCertificationDateIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.adultCertificationDateIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.adultCertificationDateIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$lastNameCertification() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.lastNameCertificationIndex);
    }

    @Override
    public void realmSet$lastNameCertification(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.lastNameCertificationIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.lastNameCertificationIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.lastNameCertificationIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.lastNameCertificationIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$emailCertification() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.emailCertificationIndex);
    }

    @Override
    public void realmSet$emailCertification(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.emailCertificationIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.emailCertificationIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.emailCertificationIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.emailCertificationIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Boolean realmGet$isVip() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.isVipIndex)) {
            return null;
        }
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.isVipIndex);
    }

    @Override
    public void realmSet$isVip(Boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.isVipIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBoolean(columnInfo.isVipIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.isVipIndex);
            return;
        }
        proxyState.getRow$realm().setBoolean(columnInfo.isVipIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Boolean realmGet$isVipActive() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.isVipActiveIndex)) {
            return null;
        }
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.isVipActiveIndex);
    }

    @Override
    public void realmSet$isVipActive(Boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.isVipActiveIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBoolean(columnInfo.isVipActiveIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.isVipActiveIndex);
            return;
        }
        proxyState.getRow$realm().setBoolean(columnInfo.isVipActiveIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$vipCreateDate() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.vipCreateDateIndex);
    }

    @Override
    public void realmSet$vipCreateDate(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.vipCreateDateIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.vipCreateDateIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.vipCreateDateIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.vipCreateDateIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$profileOriginalImage() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.profileOriginalImageIndex);
    }

    @Override
    public void realmSet$profileOriginalImage(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.profileOriginalImageIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.profileOriginalImageIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.profileOriginalImageIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.profileOriginalImageIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$clientToken() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.clientTokenIndex);
    }

    @Override
    public void realmSet$clientToken(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.clientTokenIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.clientTokenIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.clientTokenIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.clientTokenIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$onAir() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.onAirIndex);
    }

    @Override
    public void realmSet$onAir(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.onAirIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.onAirIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$token() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.tokenIndex);
    }

    @Override
    public void realmSet$token(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.tokenIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.tokenIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.tokenIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.tokenIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("ModelUser");
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedLinkProperty("user", RealmFieldType.OBJECT, "ModelChildUser");
        builder.addPersistedProperty("userNo", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("nameCertification", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("autoLogin", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("nickName", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("stateMessage", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("noticeLive", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("noticeVod", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("noticeChat", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("noticeNotice", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("noticeSound", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("noticeVibration", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("phone", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("phoneCertification", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("limitMobileData", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("stopRecentView", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("stopRecentSearch", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("profileImage", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("phoneCertificationDate", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("adultCertification", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("setPass", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("passString", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("adultCertificationDate", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("lastNameCertification", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("emailCertification", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("isVip", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("isVipActive", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("vipCreateDate", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("profileOriginalImage", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("clientToken", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("onAir", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("token", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static ModelUserColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new ModelUserColumnInfo(schemaInfo);
    }

    public static String getTableName() {
        return "class_ModelUser";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static notq.dccast.model.user.ModelUser createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(1);
        notq.dccast.model.user.ModelUser obj = null;
        if (update) {
            Table table = realm.getTable(notq.dccast.model.user.ModelUser.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(notq.dccast.model.user.ModelUser.class), false, Collections.<String> emptyList());
                    obj = new io.realm.ModelUserRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("user")) {
                excludeFields.add("user");
            }
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.ModelUserRealmProxy) realm.createObjectInternal(notq.dccast.model.user.ModelUser.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.ModelUserRealmProxy) realm.createObjectInternal(notq.dccast.model.user.ModelUser.class, json.getInt("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }

        final ModelUserRealmProxyInterface objProxy = (ModelUserRealmProxyInterface) obj;
        if (json.has("user")) {
            if (json.isNull("user")) {
                objProxy.realmSet$user(null);
            } else {
                notq.dccast.model.user.ModelChildUser userObj = ModelChildUserRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("user"), update);
                objProxy.realmSet$user(userObj);
            }
        }
        if (json.has("userNo")) {
            if (json.isNull("userNo")) {
                objProxy.realmSet$userNo(null);
            } else {
                objProxy.realmSet$userNo((String) json.getString("userNo"));
            }
        }
        if (json.has("nameCertification")) {
            if (json.isNull("nameCertification")) {
                objProxy.realmSet$nameCertification(null);
            } else {
                objProxy.realmSet$nameCertification((boolean) json.getBoolean("nameCertification"));
            }
        }
        if (json.has("autoLogin")) {
            if (json.isNull("autoLogin")) {
                objProxy.realmSet$autoLogin(null);
            } else {
                objProxy.realmSet$autoLogin((boolean) json.getBoolean("autoLogin"));
            }
        }
        if (json.has("nickName")) {
            if (json.isNull("nickName")) {
                objProxy.realmSet$nickName(null);
            } else {
                objProxy.realmSet$nickName((String) json.getString("nickName"));
            }
        }
        if (json.has("stateMessage")) {
            if (json.isNull("stateMessage")) {
                objProxy.realmSet$stateMessage(null);
            } else {
                objProxy.realmSet$stateMessage((String) json.getString("stateMessage"));
            }
        }
        if (json.has("noticeLive")) {
            if (json.isNull("noticeLive")) {
                objProxy.realmSet$noticeLive(null);
            } else {
                objProxy.realmSet$noticeLive((boolean) json.getBoolean("noticeLive"));
            }
        }
        if (json.has("noticeVod")) {
            if (json.isNull("noticeVod")) {
                objProxy.realmSet$noticeVod(null);
            } else {
                objProxy.realmSet$noticeVod((boolean) json.getBoolean("noticeVod"));
            }
        }
        if (json.has("noticeChat")) {
            if (json.isNull("noticeChat")) {
                objProxy.realmSet$noticeChat(null);
            } else {
                objProxy.realmSet$noticeChat((boolean) json.getBoolean("noticeChat"));
            }
        }
        if (json.has("noticeNotice")) {
            if (json.isNull("noticeNotice")) {
                objProxy.realmSet$noticeNotice(null);
            } else {
                objProxy.realmSet$noticeNotice((boolean) json.getBoolean("noticeNotice"));
            }
        }
        if (json.has("noticeSound")) {
            if (json.isNull("noticeSound")) {
                objProxy.realmSet$noticeSound(null);
            } else {
                objProxy.realmSet$noticeSound((boolean) json.getBoolean("noticeSound"));
            }
        }
        if (json.has("noticeVibration")) {
            if (json.isNull("noticeVibration")) {
                objProxy.realmSet$noticeVibration(null);
            } else {
                objProxy.realmSet$noticeVibration((boolean) json.getBoolean("noticeVibration"));
            }
        }
        if (json.has("phone")) {
            if (json.isNull("phone")) {
                objProxy.realmSet$phone(null);
            } else {
                objProxy.realmSet$phone((String) json.getString("phone"));
            }
        }
        if (json.has("phoneCertification")) {
            if (json.isNull("phoneCertification")) {
                objProxy.realmSet$phoneCertification(null);
            } else {
                objProxy.realmSet$phoneCertification((boolean) json.getBoolean("phoneCertification"));
            }
        }
        if (json.has("limitMobileData")) {
            if (json.isNull("limitMobileData")) {
                objProxy.realmSet$limitMobileData(null);
            } else {
                objProxy.realmSet$limitMobileData((boolean) json.getBoolean("limitMobileData"));
            }
        }
        if (json.has("stopRecentView")) {
            if (json.isNull("stopRecentView")) {
                objProxy.realmSet$stopRecentView(null);
            } else {
                objProxy.realmSet$stopRecentView((boolean) json.getBoolean("stopRecentView"));
            }
        }
        if (json.has("stopRecentSearch")) {
            if (json.isNull("stopRecentSearch")) {
                objProxy.realmSet$stopRecentSearch(null);
            } else {
                objProxy.realmSet$stopRecentSearch((boolean) json.getBoolean("stopRecentSearch"));
            }
        }
        if (json.has("profileImage")) {
            if (json.isNull("profileImage")) {
                objProxy.realmSet$profileImage(null);
            } else {
                objProxy.realmSet$profileImage((String) json.getString("profileImage"));
            }
        }
        if (json.has("phoneCertificationDate")) {
            if (json.isNull("phoneCertificationDate")) {
                objProxy.realmSet$phoneCertificationDate(null);
            } else {
                objProxy.realmSet$phoneCertificationDate((String) json.getString("phoneCertificationDate"));
            }
        }
        if (json.has("adultCertification")) {
            if (json.isNull("adultCertification")) {
                objProxy.realmSet$adultCertification(null);
            } else {
                objProxy.realmSet$adultCertification((boolean) json.getBoolean("adultCertification"));
            }
        }
        if (json.has("setPass")) {
            if (json.isNull("setPass")) {
                objProxy.realmSet$setPass(null);
            } else {
                objProxy.realmSet$setPass((boolean) json.getBoolean("setPass"));
            }
        }
        if (json.has("passString")) {
            if (json.isNull("passString")) {
                objProxy.realmSet$passString(null);
            } else {
                objProxy.realmSet$passString((String) json.getString("passString"));
            }
        }
        if (json.has("adultCertificationDate")) {
            if (json.isNull("adultCertificationDate")) {
                objProxy.realmSet$adultCertificationDate(null);
            } else {
                objProxy.realmSet$adultCertificationDate((String) json.getString("adultCertificationDate"));
            }
        }
        if (json.has("lastNameCertification")) {
            if (json.isNull("lastNameCertification")) {
                objProxy.realmSet$lastNameCertification(null);
            } else {
                objProxy.realmSet$lastNameCertification((String) json.getString("lastNameCertification"));
            }
        }
        if (json.has("emailCertification")) {
            if (json.isNull("emailCertification")) {
                objProxy.realmSet$emailCertification(null);
            } else {
                objProxy.realmSet$emailCertification((String) json.getString("emailCertification"));
            }
        }
        if (json.has("isVip")) {
            if (json.isNull("isVip")) {
                objProxy.realmSet$isVip(null);
            } else {
                objProxy.realmSet$isVip((boolean) json.getBoolean("isVip"));
            }
        }
        if (json.has("isVipActive")) {
            if (json.isNull("isVipActive")) {
                objProxy.realmSet$isVipActive(null);
            } else {
                objProxy.realmSet$isVipActive((boolean) json.getBoolean("isVipActive"));
            }
        }
        if (json.has("vipCreateDate")) {
            if (json.isNull("vipCreateDate")) {
                objProxy.realmSet$vipCreateDate(null);
            } else {
                objProxy.realmSet$vipCreateDate((String) json.getString("vipCreateDate"));
            }
        }
        if (json.has("profileOriginalImage")) {
            if (json.isNull("profileOriginalImage")) {
                objProxy.realmSet$profileOriginalImage(null);
            } else {
                objProxy.realmSet$profileOriginalImage((String) json.getString("profileOriginalImage"));
            }
        }
        if (json.has("clientToken")) {
            if (json.isNull("clientToken")) {
                objProxy.realmSet$clientToken(null);
            } else {
                objProxy.realmSet$clientToken((String) json.getString("clientToken"));
            }
        }
        if (json.has("onAir")) {
            if (json.isNull("onAir")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'onAir' to null.");
            } else {
                objProxy.realmSet$onAir((boolean) json.getBoolean("onAir"));
            }
        }
        if (json.has("token")) {
            if (json.isNull("token")) {
                objProxy.realmSet$token(null);
            } else {
                objProxy.realmSet$token((String) json.getString("token"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static notq.dccast.model.user.ModelUser createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final notq.dccast.model.user.ModelUser obj = new notq.dccast.model.user.ModelUser();
        final ModelUserRealmProxyInterface objProxy = (ModelUserRealmProxyInterface) obj;
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
            } else if (name.equals("user")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$user(null);
                } else {
                    notq.dccast.model.user.ModelChildUser userObj = ModelChildUserRealmProxy.createUsingJsonStream(realm, reader);
                    objProxy.realmSet$user(userObj);
                }
            } else if (name.equals("userNo")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$userNo((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$userNo(null);
                }
            } else if (name.equals("nameCertification")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$nameCertification((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$nameCertification(null);
                }
            } else if (name.equals("autoLogin")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$autoLogin((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$autoLogin(null);
                }
            } else if (name.equals("nickName")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$nickName((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$nickName(null);
                }
            } else if (name.equals("stateMessage")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$stateMessage((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$stateMessage(null);
                }
            } else if (name.equals("noticeLive")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$noticeLive((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$noticeLive(null);
                }
            } else if (name.equals("noticeVod")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$noticeVod((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$noticeVod(null);
                }
            } else if (name.equals("noticeChat")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$noticeChat((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$noticeChat(null);
                }
            } else if (name.equals("noticeNotice")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$noticeNotice((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$noticeNotice(null);
                }
            } else if (name.equals("noticeSound")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$noticeSound((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$noticeSound(null);
                }
            } else if (name.equals("noticeVibration")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$noticeVibration((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$noticeVibration(null);
                }
            } else if (name.equals("phone")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$phone((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$phone(null);
                }
            } else if (name.equals("phoneCertification")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$phoneCertification((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$phoneCertification(null);
                }
            } else if (name.equals("limitMobileData")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$limitMobileData((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$limitMobileData(null);
                }
            } else if (name.equals("stopRecentView")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$stopRecentView((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$stopRecentView(null);
                }
            } else if (name.equals("stopRecentSearch")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$stopRecentSearch((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$stopRecentSearch(null);
                }
            } else if (name.equals("profileImage")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$profileImage((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$profileImage(null);
                }
            } else if (name.equals("phoneCertificationDate")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$phoneCertificationDate((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$phoneCertificationDate(null);
                }
            } else if (name.equals("adultCertification")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$adultCertification((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$adultCertification(null);
                }
            } else if (name.equals("setPass")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$setPass((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$setPass(null);
                }
            } else if (name.equals("passString")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$passString((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$passString(null);
                }
            } else if (name.equals("adultCertificationDate")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$adultCertificationDate((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$adultCertificationDate(null);
                }
            } else if (name.equals("lastNameCertification")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$lastNameCertification((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$lastNameCertification(null);
                }
            } else if (name.equals("emailCertification")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$emailCertification((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$emailCertification(null);
                }
            } else if (name.equals("isVip")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$isVip((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$isVip(null);
                }
            } else if (name.equals("isVipActive")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$isVipActive((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$isVipActive(null);
                }
            } else if (name.equals("vipCreateDate")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$vipCreateDate((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$vipCreateDate(null);
                }
            } else if (name.equals("profileOriginalImage")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$profileOriginalImage((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$profileOriginalImage(null);
                }
            } else if (name.equals("clientToken")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$clientToken((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$clientToken(null);
                }
            } else if (name.equals("onAir")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$onAir((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'onAir' to null.");
                }
            } else if (name.equals("token")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$token((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$token(null);
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

    public static notq.dccast.model.user.ModelUser copyOrUpdate(Realm realm, notq.dccast.model.user.ModelUser object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (notq.dccast.model.user.ModelUser) cachedRealmObject;
        }

        notq.dccast.model.user.ModelUser realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(notq.dccast.model.user.ModelUser.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = table.findFirstLong(pkColumnIndex, ((ModelUserRealmProxyInterface) object).realmGet$id());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(notq.dccast.model.user.ModelUser.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.ModelUserRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static notq.dccast.model.user.ModelUser copy(Realm realm, notq.dccast.model.user.ModelUser newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (notq.dccast.model.user.ModelUser) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        notq.dccast.model.user.ModelUser realmObject = realm.createObjectInternal(notq.dccast.model.user.ModelUser.class, ((ModelUserRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        ModelUserRealmProxyInterface realmObjectSource = (ModelUserRealmProxyInterface) newObject;
        ModelUserRealmProxyInterface realmObjectCopy = (ModelUserRealmProxyInterface) realmObject;


        notq.dccast.model.user.ModelChildUser userObj = realmObjectSource.realmGet$user();
        if (userObj == null) {
            realmObjectCopy.realmSet$user(null);
        } else {
            notq.dccast.model.user.ModelChildUser cacheuser = (notq.dccast.model.user.ModelChildUser) cache.get(userObj);
            if (cacheuser != null) {
                realmObjectCopy.realmSet$user(cacheuser);
            } else {
                realmObjectCopy.realmSet$user(ModelChildUserRealmProxy.copyOrUpdate(realm, userObj, update, cache));
            }
        }
        realmObjectCopy.realmSet$userNo(realmObjectSource.realmGet$userNo());
        realmObjectCopy.realmSet$nameCertification(realmObjectSource.realmGet$nameCertification());
        realmObjectCopy.realmSet$autoLogin(realmObjectSource.realmGet$autoLogin());
        realmObjectCopy.realmSet$nickName(realmObjectSource.realmGet$nickName());
        realmObjectCopy.realmSet$stateMessage(realmObjectSource.realmGet$stateMessage());
        realmObjectCopy.realmSet$noticeLive(realmObjectSource.realmGet$noticeLive());
        realmObjectCopy.realmSet$noticeVod(realmObjectSource.realmGet$noticeVod());
        realmObjectCopy.realmSet$noticeChat(realmObjectSource.realmGet$noticeChat());
        realmObjectCopy.realmSet$noticeNotice(realmObjectSource.realmGet$noticeNotice());
        realmObjectCopy.realmSet$noticeSound(realmObjectSource.realmGet$noticeSound());
        realmObjectCopy.realmSet$noticeVibration(realmObjectSource.realmGet$noticeVibration());
        realmObjectCopy.realmSet$phone(realmObjectSource.realmGet$phone());
        realmObjectCopy.realmSet$phoneCertification(realmObjectSource.realmGet$phoneCertification());
        realmObjectCopy.realmSet$limitMobileData(realmObjectSource.realmGet$limitMobileData());
        realmObjectCopy.realmSet$stopRecentView(realmObjectSource.realmGet$stopRecentView());
        realmObjectCopy.realmSet$stopRecentSearch(realmObjectSource.realmGet$stopRecentSearch());
        realmObjectCopy.realmSet$profileImage(realmObjectSource.realmGet$profileImage());
        realmObjectCopy.realmSet$phoneCertificationDate(realmObjectSource.realmGet$phoneCertificationDate());
        realmObjectCopy.realmSet$adultCertification(realmObjectSource.realmGet$adultCertification());
        realmObjectCopy.realmSet$setPass(realmObjectSource.realmGet$setPass());
        realmObjectCopy.realmSet$passString(realmObjectSource.realmGet$passString());
        realmObjectCopy.realmSet$adultCertificationDate(realmObjectSource.realmGet$adultCertificationDate());
        realmObjectCopy.realmSet$lastNameCertification(realmObjectSource.realmGet$lastNameCertification());
        realmObjectCopy.realmSet$emailCertification(realmObjectSource.realmGet$emailCertification());
        realmObjectCopy.realmSet$isVip(realmObjectSource.realmGet$isVip());
        realmObjectCopy.realmSet$isVipActive(realmObjectSource.realmGet$isVipActive());
        realmObjectCopy.realmSet$vipCreateDate(realmObjectSource.realmGet$vipCreateDate());
        realmObjectCopy.realmSet$profileOriginalImage(realmObjectSource.realmGet$profileOriginalImage());
        realmObjectCopy.realmSet$clientToken(realmObjectSource.realmGet$clientToken());
        realmObjectCopy.realmSet$onAir(realmObjectSource.realmGet$onAir());
        realmObjectCopy.realmSet$token(realmObjectSource.realmGet$token());
        return realmObject;
    }

    public static long insert(Realm realm, notq.dccast.model.user.ModelUser object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(notq.dccast.model.user.ModelUser.class);
        long tableNativePtr = table.getNativePtr();
        ModelUserColumnInfo columnInfo = (ModelUserColumnInfo) realm.getSchema().getColumnInfo(notq.dccast.model.user.ModelUser.class);
        long pkColumnIndex = table.getPrimaryKey();
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((ModelUserRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((ModelUserRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, ((ModelUserRealmProxyInterface) object).realmGet$id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);

        notq.dccast.model.user.ModelChildUser userObj = ((ModelUserRealmProxyInterface) object).realmGet$user();
        if (userObj != null) {
            Long cacheuser = cache.get(userObj);
            if (cacheuser == null) {
                cacheuser = ModelChildUserRealmProxy.insert(realm, userObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.userIndex, rowIndex, cacheuser, false);
        }
        String realmGet$userNo = ((ModelUserRealmProxyInterface) object).realmGet$userNo();
        if (realmGet$userNo != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.userNoIndex, rowIndex, realmGet$userNo, false);
        }
        Boolean realmGet$nameCertification = ((ModelUserRealmProxyInterface) object).realmGet$nameCertification();
        if (realmGet$nameCertification != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.nameCertificationIndex, rowIndex, realmGet$nameCertification, false);
        }
        Boolean realmGet$autoLogin = ((ModelUserRealmProxyInterface) object).realmGet$autoLogin();
        if (realmGet$autoLogin != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.autoLoginIndex, rowIndex, realmGet$autoLogin, false);
        }
        String realmGet$nickName = ((ModelUserRealmProxyInterface) object).realmGet$nickName();
        if (realmGet$nickName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nickNameIndex, rowIndex, realmGet$nickName, false);
        }
        String realmGet$stateMessage = ((ModelUserRealmProxyInterface) object).realmGet$stateMessage();
        if (realmGet$stateMessage != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.stateMessageIndex, rowIndex, realmGet$stateMessage, false);
        }
        Boolean realmGet$noticeLive = ((ModelUserRealmProxyInterface) object).realmGet$noticeLive();
        if (realmGet$noticeLive != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeLiveIndex, rowIndex, realmGet$noticeLive, false);
        }
        Boolean realmGet$noticeVod = ((ModelUserRealmProxyInterface) object).realmGet$noticeVod();
        if (realmGet$noticeVod != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeVodIndex, rowIndex, realmGet$noticeVod, false);
        }
        Boolean realmGet$noticeChat = ((ModelUserRealmProxyInterface) object).realmGet$noticeChat();
        if (realmGet$noticeChat != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeChatIndex, rowIndex, realmGet$noticeChat, false);
        }
        Boolean realmGet$noticeNotice = ((ModelUserRealmProxyInterface) object).realmGet$noticeNotice();
        if (realmGet$noticeNotice != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeNoticeIndex, rowIndex, realmGet$noticeNotice, false);
        }
        Boolean realmGet$noticeSound = ((ModelUserRealmProxyInterface) object).realmGet$noticeSound();
        if (realmGet$noticeSound != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeSoundIndex, rowIndex, realmGet$noticeSound, false);
        }
        Boolean realmGet$noticeVibration = ((ModelUserRealmProxyInterface) object).realmGet$noticeVibration();
        if (realmGet$noticeVibration != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeVibrationIndex, rowIndex, realmGet$noticeVibration, false);
        }
        String realmGet$phone = ((ModelUserRealmProxyInterface) object).realmGet$phone();
        if (realmGet$phone != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.phoneIndex, rowIndex, realmGet$phone, false);
        }
        Boolean realmGet$phoneCertification = ((ModelUserRealmProxyInterface) object).realmGet$phoneCertification();
        if (realmGet$phoneCertification != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.phoneCertificationIndex, rowIndex, realmGet$phoneCertification, false);
        }
        Boolean realmGet$limitMobileData = ((ModelUserRealmProxyInterface) object).realmGet$limitMobileData();
        if (realmGet$limitMobileData != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.limitMobileDataIndex, rowIndex, realmGet$limitMobileData, false);
        }
        Boolean realmGet$stopRecentView = ((ModelUserRealmProxyInterface) object).realmGet$stopRecentView();
        if (realmGet$stopRecentView != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.stopRecentViewIndex, rowIndex, realmGet$stopRecentView, false);
        }
        Boolean realmGet$stopRecentSearch = ((ModelUserRealmProxyInterface) object).realmGet$stopRecentSearch();
        if (realmGet$stopRecentSearch != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.stopRecentSearchIndex, rowIndex, realmGet$stopRecentSearch, false);
        }
        String realmGet$profileImage = ((ModelUserRealmProxyInterface) object).realmGet$profileImage();
        if (realmGet$profileImage != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.profileImageIndex, rowIndex, realmGet$profileImage, false);
        }
        String realmGet$phoneCertificationDate = ((ModelUserRealmProxyInterface) object).realmGet$phoneCertificationDate();
        if (realmGet$phoneCertificationDate != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.phoneCertificationDateIndex, rowIndex, realmGet$phoneCertificationDate, false);
        }
        Boolean realmGet$adultCertification = ((ModelUserRealmProxyInterface) object).realmGet$adultCertification();
        if (realmGet$adultCertification != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.adultCertificationIndex, rowIndex, realmGet$adultCertification, false);
        }
        Boolean realmGet$setPass = ((ModelUserRealmProxyInterface) object).realmGet$setPass();
        if (realmGet$setPass != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.setPassIndex, rowIndex, realmGet$setPass, false);
        }
        String realmGet$passString = ((ModelUserRealmProxyInterface) object).realmGet$passString();
        if (realmGet$passString != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.passStringIndex, rowIndex, realmGet$passString, false);
        }
        String realmGet$adultCertificationDate = ((ModelUserRealmProxyInterface) object).realmGet$adultCertificationDate();
        if (realmGet$adultCertificationDate != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.adultCertificationDateIndex, rowIndex, realmGet$adultCertificationDate, false);
        }
        String realmGet$lastNameCertification = ((ModelUserRealmProxyInterface) object).realmGet$lastNameCertification();
        if (realmGet$lastNameCertification != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.lastNameCertificationIndex, rowIndex, realmGet$lastNameCertification, false);
        }
        String realmGet$emailCertification = ((ModelUserRealmProxyInterface) object).realmGet$emailCertification();
        if (realmGet$emailCertification != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.emailCertificationIndex, rowIndex, realmGet$emailCertification, false);
        }
        Boolean realmGet$isVip = ((ModelUserRealmProxyInterface) object).realmGet$isVip();
        if (realmGet$isVip != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.isVipIndex, rowIndex, realmGet$isVip, false);
        }
        Boolean realmGet$isVipActive = ((ModelUserRealmProxyInterface) object).realmGet$isVipActive();
        if (realmGet$isVipActive != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.isVipActiveIndex, rowIndex, realmGet$isVipActive, false);
        }
        String realmGet$vipCreateDate = ((ModelUserRealmProxyInterface) object).realmGet$vipCreateDate();
        if (realmGet$vipCreateDate != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.vipCreateDateIndex, rowIndex, realmGet$vipCreateDate, false);
        }
        String realmGet$profileOriginalImage = ((ModelUserRealmProxyInterface) object).realmGet$profileOriginalImage();
        if (realmGet$profileOriginalImage != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.profileOriginalImageIndex, rowIndex, realmGet$profileOriginalImage, false);
        }
        String realmGet$clientToken = ((ModelUserRealmProxyInterface) object).realmGet$clientToken();
        if (realmGet$clientToken != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.clientTokenIndex, rowIndex, realmGet$clientToken, false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.onAirIndex, rowIndex, ((ModelUserRealmProxyInterface) object).realmGet$onAir(), false);
        String realmGet$token = ((ModelUserRealmProxyInterface) object).realmGet$token();
        if (realmGet$token != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.tokenIndex, rowIndex, realmGet$token, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(notq.dccast.model.user.ModelUser.class);
        long tableNativePtr = table.getNativePtr();
        ModelUserColumnInfo columnInfo = (ModelUserColumnInfo) realm.getSchema().getColumnInfo(notq.dccast.model.user.ModelUser.class);
        long pkColumnIndex = table.getPrimaryKey();
        notq.dccast.model.user.ModelUser object = null;
        while (objects.hasNext()) {
            object = (notq.dccast.model.user.ModelUser) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((ModelUserRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((ModelUserRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, ((ModelUserRealmProxyInterface) object).realmGet$id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);

            notq.dccast.model.user.ModelChildUser userObj = ((ModelUserRealmProxyInterface) object).realmGet$user();
            if (userObj != null) {
                Long cacheuser = cache.get(userObj);
                if (cacheuser == null) {
                    cacheuser = ModelChildUserRealmProxy.insert(realm, userObj, cache);
                }
                table.setLink(columnInfo.userIndex, rowIndex, cacheuser, false);
            }
            String realmGet$userNo = ((ModelUserRealmProxyInterface) object).realmGet$userNo();
            if (realmGet$userNo != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.userNoIndex, rowIndex, realmGet$userNo, false);
            }
            Boolean realmGet$nameCertification = ((ModelUserRealmProxyInterface) object).realmGet$nameCertification();
            if (realmGet$nameCertification != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.nameCertificationIndex, rowIndex, realmGet$nameCertification, false);
            }
            Boolean realmGet$autoLogin = ((ModelUserRealmProxyInterface) object).realmGet$autoLogin();
            if (realmGet$autoLogin != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.autoLoginIndex, rowIndex, realmGet$autoLogin, false);
            }
            String realmGet$nickName = ((ModelUserRealmProxyInterface) object).realmGet$nickName();
            if (realmGet$nickName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.nickNameIndex, rowIndex, realmGet$nickName, false);
            }
            String realmGet$stateMessage = ((ModelUserRealmProxyInterface) object).realmGet$stateMessage();
            if (realmGet$stateMessage != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.stateMessageIndex, rowIndex, realmGet$stateMessage, false);
            }
            Boolean realmGet$noticeLive = ((ModelUserRealmProxyInterface) object).realmGet$noticeLive();
            if (realmGet$noticeLive != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeLiveIndex, rowIndex, realmGet$noticeLive, false);
            }
            Boolean realmGet$noticeVod = ((ModelUserRealmProxyInterface) object).realmGet$noticeVod();
            if (realmGet$noticeVod != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeVodIndex, rowIndex, realmGet$noticeVod, false);
            }
            Boolean realmGet$noticeChat = ((ModelUserRealmProxyInterface) object).realmGet$noticeChat();
            if (realmGet$noticeChat != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeChatIndex, rowIndex, realmGet$noticeChat, false);
            }
            Boolean realmGet$noticeNotice = ((ModelUserRealmProxyInterface) object).realmGet$noticeNotice();
            if (realmGet$noticeNotice != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeNoticeIndex, rowIndex, realmGet$noticeNotice, false);
            }
            Boolean realmGet$noticeSound = ((ModelUserRealmProxyInterface) object).realmGet$noticeSound();
            if (realmGet$noticeSound != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeSoundIndex, rowIndex, realmGet$noticeSound, false);
            }
            Boolean realmGet$noticeVibration = ((ModelUserRealmProxyInterface) object).realmGet$noticeVibration();
            if (realmGet$noticeVibration != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeVibrationIndex, rowIndex, realmGet$noticeVibration, false);
            }
            String realmGet$phone = ((ModelUserRealmProxyInterface) object).realmGet$phone();
            if (realmGet$phone != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.phoneIndex, rowIndex, realmGet$phone, false);
            }
            Boolean realmGet$phoneCertification = ((ModelUserRealmProxyInterface) object).realmGet$phoneCertification();
            if (realmGet$phoneCertification != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.phoneCertificationIndex, rowIndex, realmGet$phoneCertification, false);
            }
            Boolean realmGet$limitMobileData = ((ModelUserRealmProxyInterface) object).realmGet$limitMobileData();
            if (realmGet$limitMobileData != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.limitMobileDataIndex, rowIndex, realmGet$limitMobileData, false);
            }
            Boolean realmGet$stopRecentView = ((ModelUserRealmProxyInterface) object).realmGet$stopRecentView();
            if (realmGet$stopRecentView != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.stopRecentViewIndex, rowIndex, realmGet$stopRecentView, false);
            }
            Boolean realmGet$stopRecentSearch = ((ModelUserRealmProxyInterface) object).realmGet$stopRecentSearch();
            if (realmGet$stopRecentSearch != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.stopRecentSearchIndex, rowIndex, realmGet$stopRecentSearch, false);
            }
            String realmGet$profileImage = ((ModelUserRealmProxyInterface) object).realmGet$profileImage();
            if (realmGet$profileImage != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.profileImageIndex, rowIndex, realmGet$profileImage, false);
            }
            String realmGet$phoneCertificationDate = ((ModelUserRealmProxyInterface) object).realmGet$phoneCertificationDate();
            if (realmGet$phoneCertificationDate != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.phoneCertificationDateIndex, rowIndex, realmGet$phoneCertificationDate, false);
            }
            Boolean realmGet$adultCertification = ((ModelUserRealmProxyInterface) object).realmGet$adultCertification();
            if (realmGet$adultCertification != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.adultCertificationIndex, rowIndex, realmGet$adultCertification, false);
            }
            Boolean realmGet$setPass = ((ModelUserRealmProxyInterface) object).realmGet$setPass();
            if (realmGet$setPass != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.setPassIndex, rowIndex, realmGet$setPass, false);
            }
            String realmGet$passString = ((ModelUserRealmProxyInterface) object).realmGet$passString();
            if (realmGet$passString != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.passStringIndex, rowIndex, realmGet$passString, false);
            }
            String realmGet$adultCertificationDate = ((ModelUserRealmProxyInterface) object).realmGet$adultCertificationDate();
            if (realmGet$adultCertificationDate != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.adultCertificationDateIndex, rowIndex, realmGet$adultCertificationDate, false);
            }
            String realmGet$lastNameCertification = ((ModelUserRealmProxyInterface) object).realmGet$lastNameCertification();
            if (realmGet$lastNameCertification != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.lastNameCertificationIndex, rowIndex, realmGet$lastNameCertification, false);
            }
            String realmGet$emailCertification = ((ModelUserRealmProxyInterface) object).realmGet$emailCertification();
            if (realmGet$emailCertification != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.emailCertificationIndex, rowIndex, realmGet$emailCertification, false);
            }
            Boolean realmGet$isVip = ((ModelUserRealmProxyInterface) object).realmGet$isVip();
            if (realmGet$isVip != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.isVipIndex, rowIndex, realmGet$isVip, false);
            }
            Boolean realmGet$isVipActive = ((ModelUserRealmProxyInterface) object).realmGet$isVipActive();
            if (realmGet$isVipActive != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.isVipActiveIndex, rowIndex, realmGet$isVipActive, false);
            }
            String realmGet$vipCreateDate = ((ModelUserRealmProxyInterface) object).realmGet$vipCreateDate();
            if (realmGet$vipCreateDate != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.vipCreateDateIndex, rowIndex, realmGet$vipCreateDate, false);
            }
            String realmGet$profileOriginalImage = ((ModelUserRealmProxyInterface) object).realmGet$profileOriginalImage();
            if (realmGet$profileOriginalImage != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.profileOriginalImageIndex, rowIndex, realmGet$profileOriginalImage, false);
            }
            String realmGet$clientToken = ((ModelUserRealmProxyInterface) object).realmGet$clientToken();
            if (realmGet$clientToken != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.clientTokenIndex, rowIndex, realmGet$clientToken, false);
            }
            Table.nativeSetBoolean(tableNativePtr, columnInfo.onAirIndex, rowIndex, ((ModelUserRealmProxyInterface) object).realmGet$onAir(), false);
            String realmGet$token = ((ModelUserRealmProxyInterface) object).realmGet$token();
            if (realmGet$token != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.tokenIndex, rowIndex, realmGet$token, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, notq.dccast.model.user.ModelUser object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(notq.dccast.model.user.ModelUser.class);
        long tableNativePtr = table.getNativePtr();
        ModelUserColumnInfo columnInfo = (ModelUserColumnInfo) realm.getSchema().getColumnInfo(notq.dccast.model.user.ModelUser.class);
        long pkColumnIndex = table.getPrimaryKey();
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((ModelUserRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((ModelUserRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, ((ModelUserRealmProxyInterface) object).realmGet$id());
        }
        cache.put(object, rowIndex);

        notq.dccast.model.user.ModelChildUser userObj = ((ModelUserRealmProxyInterface) object).realmGet$user();
        if (userObj != null) {
            Long cacheuser = cache.get(userObj);
            if (cacheuser == null) {
                cacheuser = ModelChildUserRealmProxy.insertOrUpdate(realm, userObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.userIndex, rowIndex, cacheuser, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.userIndex, rowIndex);
        }
        String realmGet$userNo = ((ModelUserRealmProxyInterface) object).realmGet$userNo();
        if (realmGet$userNo != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.userNoIndex, rowIndex, realmGet$userNo, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.userNoIndex, rowIndex, false);
        }
        Boolean realmGet$nameCertification = ((ModelUserRealmProxyInterface) object).realmGet$nameCertification();
        if (realmGet$nameCertification != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.nameCertificationIndex, rowIndex, realmGet$nameCertification, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.nameCertificationIndex, rowIndex, false);
        }
        Boolean realmGet$autoLogin = ((ModelUserRealmProxyInterface) object).realmGet$autoLogin();
        if (realmGet$autoLogin != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.autoLoginIndex, rowIndex, realmGet$autoLogin, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.autoLoginIndex, rowIndex, false);
        }
        String realmGet$nickName = ((ModelUserRealmProxyInterface) object).realmGet$nickName();
        if (realmGet$nickName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nickNameIndex, rowIndex, realmGet$nickName, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.nickNameIndex, rowIndex, false);
        }
        String realmGet$stateMessage = ((ModelUserRealmProxyInterface) object).realmGet$stateMessage();
        if (realmGet$stateMessage != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.stateMessageIndex, rowIndex, realmGet$stateMessage, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.stateMessageIndex, rowIndex, false);
        }
        Boolean realmGet$noticeLive = ((ModelUserRealmProxyInterface) object).realmGet$noticeLive();
        if (realmGet$noticeLive != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeLiveIndex, rowIndex, realmGet$noticeLive, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.noticeLiveIndex, rowIndex, false);
        }
        Boolean realmGet$noticeVod = ((ModelUserRealmProxyInterface) object).realmGet$noticeVod();
        if (realmGet$noticeVod != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeVodIndex, rowIndex, realmGet$noticeVod, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.noticeVodIndex, rowIndex, false);
        }
        Boolean realmGet$noticeChat = ((ModelUserRealmProxyInterface) object).realmGet$noticeChat();
        if (realmGet$noticeChat != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeChatIndex, rowIndex, realmGet$noticeChat, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.noticeChatIndex, rowIndex, false);
        }
        Boolean realmGet$noticeNotice = ((ModelUserRealmProxyInterface) object).realmGet$noticeNotice();
        if (realmGet$noticeNotice != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeNoticeIndex, rowIndex, realmGet$noticeNotice, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.noticeNoticeIndex, rowIndex, false);
        }
        Boolean realmGet$noticeSound = ((ModelUserRealmProxyInterface) object).realmGet$noticeSound();
        if (realmGet$noticeSound != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeSoundIndex, rowIndex, realmGet$noticeSound, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.noticeSoundIndex, rowIndex, false);
        }
        Boolean realmGet$noticeVibration = ((ModelUserRealmProxyInterface) object).realmGet$noticeVibration();
        if (realmGet$noticeVibration != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeVibrationIndex, rowIndex, realmGet$noticeVibration, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.noticeVibrationIndex, rowIndex, false);
        }
        String realmGet$phone = ((ModelUserRealmProxyInterface) object).realmGet$phone();
        if (realmGet$phone != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.phoneIndex, rowIndex, realmGet$phone, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.phoneIndex, rowIndex, false);
        }
        Boolean realmGet$phoneCertification = ((ModelUserRealmProxyInterface) object).realmGet$phoneCertification();
        if (realmGet$phoneCertification != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.phoneCertificationIndex, rowIndex, realmGet$phoneCertification, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.phoneCertificationIndex, rowIndex, false);
        }
        Boolean realmGet$limitMobileData = ((ModelUserRealmProxyInterface) object).realmGet$limitMobileData();
        if (realmGet$limitMobileData != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.limitMobileDataIndex, rowIndex, realmGet$limitMobileData, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.limitMobileDataIndex, rowIndex, false);
        }
        Boolean realmGet$stopRecentView = ((ModelUserRealmProxyInterface) object).realmGet$stopRecentView();
        if (realmGet$stopRecentView != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.stopRecentViewIndex, rowIndex, realmGet$stopRecentView, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.stopRecentViewIndex, rowIndex, false);
        }
        Boolean realmGet$stopRecentSearch = ((ModelUserRealmProxyInterface) object).realmGet$stopRecentSearch();
        if (realmGet$stopRecentSearch != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.stopRecentSearchIndex, rowIndex, realmGet$stopRecentSearch, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.stopRecentSearchIndex, rowIndex, false);
        }
        String realmGet$profileImage = ((ModelUserRealmProxyInterface) object).realmGet$profileImage();
        if (realmGet$profileImage != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.profileImageIndex, rowIndex, realmGet$profileImage, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.profileImageIndex, rowIndex, false);
        }
        String realmGet$phoneCertificationDate = ((ModelUserRealmProxyInterface) object).realmGet$phoneCertificationDate();
        if (realmGet$phoneCertificationDate != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.phoneCertificationDateIndex, rowIndex, realmGet$phoneCertificationDate, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.phoneCertificationDateIndex, rowIndex, false);
        }
        Boolean realmGet$adultCertification = ((ModelUserRealmProxyInterface) object).realmGet$adultCertification();
        if (realmGet$adultCertification != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.adultCertificationIndex, rowIndex, realmGet$adultCertification, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.adultCertificationIndex, rowIndex, false);
        }
        Boolean realmGet$setPass = ((ModelUserRealmProxyInterface) object).realmGet$setPass();
        if (realmGet$setPass != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.setPassIndex, rowIndex, realmGet$setPass, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.setPassIndex, rowIndex, false);
        }
        String realmGet$passString = ((ModelUserRealmProxyInterface) object).realmGet$passString();
        if (realmGet$passString != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.passStringIndex, rowIndex, realmGet$passString, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.passStringIndex, rowIndex, false);
        }
        String realmGet$adultCertificationDate = ((ModelUserRealmProxyInterface) object).realmGet$adultCertificationDate();
        if (realmGet$adultCertificationDate != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.adultCertificationDateIndex, rowIndex, realmGet$adultCertificationDate, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.adultCertificationDateIndex, rowIndex, false);
        }
        String realmGet$lastNameCertification = ((ModelUserRealmProxyInterface) object).realmGet$lastNameCertification();
        if (realmGet$lastNameCertification != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.lastNameCertificationIndex, rowIndex, realmGet$lastNameCertification, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.lastNameCertificationIndex, rowIndex, false);
        }
        String realmGet$emailCertification = ((ModelUserRealmProxyInterface) object).realmGet$emailCertification();
        if (realmGet$emailCertification != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.emailCertificationIndex, rowIndex, realmGet$emailCertification, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.emailCertificationIndex, rowIndex, false);
        }
        Boolean realmGet$isVip = ((ModelUserRealmProxyInterface) object).realmGet$isVip();
        if (realmGet$isVip != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.isVipIndex, rowIndex, realmGet$isVip, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.isVipIndex, rowIndex, false);
        }
        Boolean realmGet$isVipActive = ((ModelUserRealmProxyInterface) object).realmGet$isVipActive();
        if (realmGet$isVipActive != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.isVipActiveIndex, rowIndex, realmGet$isVipActive, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.isVipActiveIndex, rowIndex, false);
        }
        String realmGet$vipCreateDate = ((ModelUserRealmProxyInterface) object).realmGet$vipCreateDate();
        if (realmGet$vipCreateDate != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.vipCreateDateIndex, rowIndex, realmGet$vipCreateDate, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.vipCreateDateIndex, rowIndex, false);
        }
        String realmGet$profileOriginalImage = ((ModelUserRealmProxyInterface) object).realmGet$profileOriginalImage();
        if (realmGet$profileOriginalImage != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.profileOriginalImageIndex, rowIndex, realmGet$profileOriginalImage, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.profileOriginalImageIndex, rowIndex, false);
        }
        String realmGet$clientToken = ((ModelUserRealmProxyInterface) object).realmGet$clientToken();
        if (realmGet$clientToken != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.clientTokenIndex, rowIndex, realmGet$clientToken, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.clientTokenIndex, rowIndex, false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.onAirIndex, rowIndex, ((ModelUserRealmProxyInterface) object).realmGet$onAir(), false);
        String realmGet$token = ((ModelUserRealmProxyInterface) object).realmGet$token();
        if (realmGet$token != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.tokenIndex, rowIndex, realmGet$token, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.tokenIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(notq.dccast.model.user.ModelUser.class);
        long tableNativePtr = table.getNativePtr();
        ModelUserColumnInfo columnInfo = (ModelUserColumnInfo) realm.getSchema().getColumnInfo(notq.dccast.model.user.ModelUser.class);
        long pkColumnIndex = table.getPrimaryKey();
        notq.dccast.model.user.ModelUser object = null;
        while (objects.hasNext()) {
            object = (notq.dccast.model.user.ModelUser) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((ModelUserRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((ModelUserRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, ((ModelUserRealmProxyInterface) object).realmGet$id());
            }
            cache.put(object, rowIndex);

            notq.dccast.model.user.ModelChildUser userObj = ((ModelUserRealmProxyInterface) object).realmGet$user();
            if (userObj != null) {
                Long cacheuser = cache.get(userObj);
                if (cacheuser == null) {
                    cacheuser = ModelChildUserRealmProxy.insertOrUpdate(realm, userObj, cache);
                }
                Table.nativeSetLink(tableNativePtr, columnInfo.userIndex, rowIndex, cacheuser, false);
            } else {
                Table.nativeNullifyLink(tableNativePtr, columnInfo.userIndex, rowIndex);
            }
            String realmGet$userNo = ((ModelUserRealmProxyInterface) object).realmGet$userNo();
            if (realmGet$userNo != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.userNoIndex, rowIndex, realmGet$userNo, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.userNoIndex, rowIndex, false);
            }
            Boolean realmGet$nameCertification = ((ModelUserRealmProxyInterface) object).realmGet$nameCertification();
            if (realmGet$nameCertification != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.nameCertificationIndex, rowIndex, realmGet$nameCertification, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.nameCertificationIndex, rowIndex, false);
            }
            Boolean realmGet$autoLogin = ((ModelUserRealmProxyInterface) object).realmGet$autoLogin();
            if (realmGet$autoLogin != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.autoLoginIndex, rowIndex, realmGet$autoLogin, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.autoLoginIndex, rowIndex, false);
            }
            String realmGet$nickName = ((ModelUserRealmProxyInterface) object).realmGet$nickName();
            if (realmGet$nickName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.nickNameIndex, rowIndex, realmGet$nickName, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.nickNameIndex, rowIndex, false);
            }
            String realmGet$stateMessage = ((ModelUserRealmProxyInterface) object).realmGet$stateMessage();
            if (realmGet$stateMessage != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.stateMessageIndex, rowIndex, realmGet$stateMessage, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.stateMessageIndex, rowIndex, false);
            }
            Boolean realmGet$noticeLive = ((ModelUserRealmProxyInterface) object).realmGet$noticeLive();
            if (realmGet$noticeLive != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeLiveIndex, rowIndex, realmGet$noticeLive, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.noticeLiveIndex, rowIndex, false);
            }
            Boolean realmGet$noticeVod = ((ModelUserRealmProxyInterface) object).realmGet$noticeVod();
            if (realmGet$noticeVod != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeVodIndex, rowIndex, realmGet$noticeVod, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.noticeVodIndex, rowIndex, false);
            }
            Boolean realmGet$noticeChat = ((ModelUserRealmProxyInterface) object).realmGet$noticeChat();
            if (realmGet$noticeChat != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeChatIndex, rowIndex, realmGet$noticeChat, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.noticeChatIndex, rowIndex, false);
            }
            Boolean realmGet$noticeNotice = ((ModelUserRealmProxyInterface) object).realmGet$noticeNotice();
            if (realmGet$noticeNotice != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeNoticeIndex, rowIndex, realmGet$noticeNotice, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.noticeNoticeIndex, rowIndex, false);
            }
            Boolean realmGet$noticeSound = ((ModelUserRealmProxyInterface) object).realmGet$noticeSound();
            if (realmGet$noticeSound != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeSoundIndex, rowIndex, realmGet$noticeSound, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.noticeSoundIndex, rowIndex, false);
            }
            Boolean realmGet$noticeVibration = ((ModelUserRealmProxyInterface) object).realmGet$noticeVibration();
            if (realmGet$noticeVibration != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.noticeVibrationIndex, rowIndex, realmGet$noticeVibration, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.noticeVibrationIndex, rowIndex, false);
            }
            String realmGet$phone = ((ModelUserRealmProxyInterface) object).realmGet$phone();
            if (realmGet$phone != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.phoneIndex, rowIndex, realmGet$phone, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.phoneIndex, rowIndex, false);
            }
            Boolean realmGet$phoneCertification = ((ModelUserRealmProxyInterface) object).realmGet$phoneCertification();
            if (realmGet$phoneCertification != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.phoneCertificationIndex, rowIndex, realmGet$phoneCertification, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.phoneCertificationIndex, rowIndex, false);
            }
            Boolean realmGet$limitMobileData = ((ModelUserRealmProxyInterface) object).realmGet$limitMobileData();
            if (realmGet$limitMobileData != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.limitMobileDataIndex, rowIndex, realmGet$limitMobileData, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.limitMobileDataIndex, rowIndex, false);
            }
            Boolean realmGet$stopRecentView = ((ModelUserRealmProxyInterface) object).realmGet$stopRecentView();
            if (realmGet$stopRecentView != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.stopRecentViewIndex, rowIndex, realmGet$stopRecentView, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.stopRecentViewIndex, rowIndex, false);
            }
            Boolean realmGet$stopRecentSearch = ((ModelUserRealmProxyInterface) object).realmGet$stopRecentSearch();
            if (realmGet$stopRecentSearch != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.stopRecentSearchIndex, rowIndex, realmGet$stopRecentSearch, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.stopRecentSearchIndex, rowIndex, false);
            }
            String realmGet$profileImage = ((ModelUserRealmProxyInterface) object).realmGet$profileImage();
            if (realmGet$profileImage != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.profileImageIndex, rowIndex, realmGet$profileImage, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.profileImageIndex, rowIndex, false);
            }
            String realmGet$phoneCertificationDate = ((ModelUserRealmProxyInterface) object).realmGet$phoneCertificationDate();
            if (realmGet$phoneCertificationDate != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.phoneCertificationDateIndex, rowIndex, realmGet$phoneCertificationDate, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.phoneCertificationDateIndex, rowIndex, false);
            }
            Boolean realmGet$adultCertification = ((ModelUserRealmProxyInterface) object).realmGet$adultCertification();
            if (realmGet$adultCertification != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.adultCertificationIndex, rowIndex, realmGet$adultCertification, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.adultCertificationIndex, rowIndex, false);
            }
            Boolean realmGet$setPass = ((ModelUserRealmProxyInterface) object).realmGet$setPass();
            if (realmGet$setPass != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.setPassIndex, rowIndex, realmGet$setPass, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.setPassIndex, rowIndex, false);
            }
            String realmGet$passString = ((ModelUserRealmProxyInterface) object).realmGet$passString();
            if (realmGet$passString != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.passStringIndex, rowIndex, realmGet$passString, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.passStringIndex, rowIndex, false);
            }
            String realmGet$adultCertificationDate = ((ModelUserRealmProxyInterface) object).realmGet$adultCertificationDate();
            if (realmGet$adultCertificationDate != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.adultCertificationDateIndex, rowIndex, realmGet$adultCertificationDate, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.adultCertificationDateIndex, rowIndex, false);
            }
            String realmGet$lastNameCertification = ((ModelUserRealmProxyInterface) object).realmGet$lastNameCertification();
            if (realmGet$lastNameCertification != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.lastNameCertificationIndex, rowIndex, realmGet$lastNameCertification, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.lastNameCertificationIndex, rowIndex, false);
            }
            String realmGet$emailCertification = ((ModelUserRealmProxyInterface) object).realmGet$emailCertification();
            if (realmGet$emailCertification != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.emailCertificationIndex, rowIndex, realmGet$emailCertification, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.emailCertificationIndex, rowIndex, false);
            }
            Boolean realmGet$isVip = ((ModelUserRealmProxyInterface) object).realmGet$isVip();
            if (realmGet$isVip != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.isVipIndex, rowIndex, realmGet$isVip, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.isVipIndex, rowIndex, false);
            }
            Boolean realmGet$isVipActive = ((ModelUserRealmProxyInterface) object).realmGet$isVipActive();
            if (realmGet$isVipActive != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.isVipActiveIndex, rowIndex, realmGet$isVipActive, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.isVipActiveIndex, rowIndex, false);
            }
            String realmGet$vipCreateDate = ((ModelUserRealmProxyInterface) object).realmGet$vipCreateDate();
            if (realmGet$vipCreateDate != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.vipCreateDateIndex, rowIndex, realmGet$vipCreateDate, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.vipCreateDateIndex, rowIndex, false);
            }
            String realmGet$profileOriginalImage = ((ModelUserRealmProxyInterface) object).realmGet$profileOriginalImage();
            if (realmGet$profileOriginalImage != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.profileOriginalImageIndex, rowIndex, realmGet$profileOriginalImage, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.profileOriginalImageIndex, rowIndex, false);
            }
            String realmGet$clientToken = ((ModelUserRealmProxyInterface) object).realmGet$clientToken();
            if (realmGet$clientToken != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.clientTokenIndex, rowIndex, realmGet$clientToken, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.clientTokenIndex, rowIndex, false);
            }
            Table.nativeSetBoolean(tableNativePtr, columnInfo.onAirIndex, rowIndex, ((ModelUserRealmProxyInterface) object).realmGet$onAir(), false);
            String realmGet$token = ((ModelUserRealmProxyInterface) object).realmGet$token();
            if (realmGet$token != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.tokenIndex, rowIndex, realmGet$token, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.tokenIndex, rowIndex, false);
            }
        }
    }

    public static notq.dccast.model.user.ModelUser createDetachedCopy(notq.dccast.model.user.ModelUser realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        notq.dccast.model.user.ModelUser unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new notq.dccast.model.user.ModelUser();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (notq.dccast.model.user.ModelUser) cachedObject.object;
            }
            unmanagedObject = (notq.dccast.model.user.ModelUser) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        ModelUserRealmProxyInterface unmanagedCopy = (ModelUserRealmProxyInterface) unmanagedObject;
        ModelUserRealmProxyInterface realmSource = (ModelUserRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());

        // Deep copy of user
        unmanagedCopy.realmSet$user(ModelChildUserRealmProxy.createDetachedCopy(realmSource.realmGet$user(), currentDepth + 1, maxDepth, cache));
        unmanagedCopy.realmSet$userNo(realmSource.realmGet$userNo());
        unmanagedCopy.realmSet$nameCertification(realmSource.realmGet$nameCertification());
        unmanagedCopy.realmSet$autoLogin(realmSource.realmGet$autoLogin());
        unmanagedCopy.realmSet$nickName(realmSource.realmGet$nickName());
        unmanagedCopy.realmSet$stateMessage(realmSource.realmGet$stateMessage());
        unmanagedCopy.realmSet$noticeLive(realmSource.realmGet$noticeLive());
        unmanagedCopy.realmSet$noticeVod(realmSource.realmGet$noticeVod());
        unmanagedCopy.realmSet$noticeChat(realmSource.realmGet$noticeChat());
        unmanagedCopy.realmSet$noticeNotice(realmSource.realmGet$noticeNotice());
        unmanagedCopy.realmSet$noticeSound(realmSource.realmGet$noticeSound());
        unmanagedCopy.realmSet$noticeVibration(realmSource.realmGet$noticeVibration());
        unmanagedCopy.realmSet$phone(realmSource.realmGet$phone());
        unmanagedCopy.realmSet$phoneCertification(realmSource.realmGet$phoneCertification());
        unmanagedCopy.realmSet$limitMobileData(realmSource.realmGet$limitMobileData());
        unmanagedCopy.realmSet$stopRecentView(realmSource.realmGet$stopRecentView());
        unmanagedCopy.realmSet$stopRecentSearch(realmSource.realmGet$stopRecentSearch());
        unmanagedCopy.realmSet$profileImage(realmSource.realmGet$profileImage());
        unmanagedCopy.realmSet$phoneCertificationDate(realmSource.realmGet$phoneCertificationDate());
        unmanagedCopy.realmSet$adultCertification(realmSource.realmGet$adultCertification());
        unmanagedCopy.realmSet$setPass(realmSource.realmGet$setPass());
        unmanagedCopy.realmSet$passString(realmSource.realmGet$passString());
        unmanagedCopy.realmSet$adultCertificationDate(realmSource.realmGet$adultCertificationDate());
        unmanagedCopy.realmSet$lastNameCertification(realmSource.realmGet$lastNameCertification());
        unmanagedCopy.realmSet$emailCertification(realmSource.realmGet$emailCertification());
        unmanagedCopy.realmSet$isVip(realmSource.realmGet$isVip());
        unmanagedCopy.realmSet$isVipActive(realmSource.realmGet$isVipActive());
        unmanagedCopy.realmSet$vipCreateDate(realmSource.realmGet$vipCreateDate());
        unmanagedCopy.realmSet$profileOriginalImage(realmSource.realmGet$profileOriginalImage());
        unmanagedCopy.realmSet$clientToken(realmSource.realmGet$clientToken());
        unmanagedCopy.realmSet$onAir(realmSource.realmGet$onAir());
        unmanagedCopy.realmSet$token(realmSource.realmGet$token());
        return unmanagedObject;
    }

    static notq.dccast.model.user.ModelUser update(Realm realm, notq.dccast.model.user.ModelUser realmObject, notq.dccast.model.user.ModelUser newObject, Map<RealmModel, RealmObjectProxy> cache) {
        ModelUserRealmProxyInterface realmObjectTarget = (ModelUserRealmProxyInterface) realmObject;
        ModelUserRealmProxyInterface realmObjectSource = (ModelUserRealmProxyInterface) newObject;
        notq.dccast.model.user.ModelChildUser userObj = realmObjectSource.realmGet$user();
        if (userObj == null) {
            realmObjectTarget.realmSet$user(null);
        } else {
            notq.dccast.model.user.ModelChildUser cacheuser = (notq.dccast.model.user.ModelChildUser) cache.get(userObj);
            if (cacheuser != null) {
                realmObjectTarget.realmSet$user(cacheuser);
            } else {
                realmObjectTarget.realmSet$user(ModelChildUserRealmProxy.copyOrUpdate(realm, userObj, true, cache));
            }
        }
        realmObjectTarget.realmSet$userNo(realmObjectSource.realmGet$userNo());
        realmObjectTarget.realmSet$nameCertification(realmObjectSource.realmGet$nameCertification());
        realmObjectTarget.realmSet$autoLogin(realmObjectSource.realmGet$autoLogin());
        realmObjectTarget.realmSet$nickName(realmObjectSource.realmGet$nickName());
        realmObjectTarget.realmSet$stateMessage(realmObjectSource.realmGet$stateMessage());
        realmObjectTarget.realmSet$noticeLive(realmObjectSource.realmGet$noticeLive());
        realmObjectTarget.realmSet$noticeVod(realmObjectSource.realmGet$noticeVod());
        realmObjectTarget.realmSet$noticeChat(realmObjectSource.realmGet$noticeChat());
        realmObjectTarget.realmSet$noticeNotice(realmObjectSource.realmGet$noticeNotice());
        realmObjectTarget.realmSet$noticeSound(realmObjectSource.realmGet$noticeSound());
        realmObjectTarget.realmSet$noticeVibration(realmObjectSource.realmGet$noticeVibration());
        realmObjectTarget.realmSet$phone(realmObjectSource.realmGet$phone());
        realmObjectTarget.realmSet$phoneCertification(realmObjectSource.realmGet$phoneCertification());
        realmObjectTarget.realmSet$limitMobileData(realmObjectSource.realmGet$limitMobileData());
        realmObjectTarget.realmSet$stopRecentView(realmObjectSource.realmGet$stopRecentView());
        realmObjectTarget.realmSet$stopRecentSearch(realmObjectSource.realmGet$stopRecentSearch());
        realmObjectTarget.realmSet$profileImage(realmObjectSource.realmGet$profileImage());
        realmObjectTarget.realmSet$phoneCertificationDate(realmObjectSource.realmGet$phoneCertificationDate());
        realmObjectTarget.realmSet$adultCertification(realmObjectSource.realmGet$adultCertification());
        realmObjectTarget.realmSet$setPass(realmObjectSource.realmGet$setPass());
        realmObjectTarget.realmSet$passString(realmObjectSource.realmGet$passString());
        realmObjectTarget.realmSet$adultCertificationDate(realmObjectSource.realmGet$adultCertificationDate());
        realmObjectTarget.realmSet$lastNameCertification(realmObjectSource.realmGet$lastNameCertification());
        realmObjectTarget.realmSet$emailCertification(realmObjectSource.realmGet$emailCertification());
        realmObjectTarget.realmSet$isVip(realmObjectSource.realmGet$isVip());
        realmObjectTarget.realmSet$isVipActive(realmObjectSource.realmGet$isVipActive());
        realmObjectTarget.realmSet$vipCreateDate(realmObjectSource.realmGet$vipCreateDate());
        realmObjectTarget.realmSet$profileOriginalImage(realmObjectSource.realmGet$profileOriginalImage());
        realmObjectTarget.realmSet$clientToken(realmObjectSource.realmGet$clientToken());
        realmObjectTarget.realmSet$onAir(realmObjectSource.realmGet$onAir());
        realmObjectTarget.realmSet$token(realmObjectSource.realmGet$token());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("ModelUser = proxy[");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{user:");
        stringBuilder.append(realmGet$user() != null ? "ModelChildUser" : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{userNo:");
        stringBuilder.append(realmGet$userNo() != null ? realmGet$userNo() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{nameCertification:");
        stringBuilder.append(realmGet$nameCertification() != null ? realmGet$nameCertification() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{autoLogin:");
        stringBuilder.append(realmGet$autoLogin() != null ? realmGet$autoLogin() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{nickName:");
        stringBuilder.append(realmGet$nickName() != null ? realmGet$nickName() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{stateMessage:");
        stringBuilder.append(realmGet$stateMessage() != null ? realmGet$stateMessage() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{noticeLive:");
        stringBuilder.append(realmGet$noticeLive() != null ? realmGet$noticeLive() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{noticeVod:");
        stringBuilder.append(realmGet$noticeVod() != null ? realmGet$noticeVod() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{noticeChat:");
        stringBuilder.append(realmGet$noticeChat() != null ? realmGet$noticeChat() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{noticeNotice:");
        stringBuilder.append(realmGet$noticeNotice() != null ? realmGet$noticeNotice() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{noticeSound:");
        stringBuilder.append(realmGet$noticeSound() != null ? realmGet$noticeSound() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{noticeVibration:");
        stringBuilder.append(realmGet$noticeVibration() != null ? realmGet$noticeVibration() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{phone:");
        stringBuilder.append(realmGet$phone() != null ? realmGet$phone() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{phoneCertification:");
        stringBuilder.append(realmGet$phoneCertification() != null ? realmGet$phoneCertification() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{limitMobileData:");
        stringBuilder.append(realmGet$limitMobileData() != null ? realmGet$limitMobileData() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{stopRecentView:");
        stringBuilder.append(realmGet$stopRecentView() != null ? realmGet$stopRecentView() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{stopRecentSearch:");
        stringBuilder.append(realmGet$stopRecentSearch() != null ? realmGet$stopRecentSearch() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{profileImage:");
        stringBuilder.append(realmGet$profileImage() != null ? realmGet$profileImage() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{phoneCertificationDate:");
        stringBuilder.append(realmGet$phoneCertificationDate() != null ? realmGet$phoneCertificationDate() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{adultCertification:");
        stringBuilder.append(realmGet$adultCertification() != null ? realmGet$adultCertification() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{setPass:");
        stringBuilder.append(realmGet$setPass() != null ? realmGet$setPass() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{passString:");
        stringBuilder.append(realmGet$passString() != null ? realmGet$passString() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{adultCertificationDate:");
        stringBuilder.append(realmGet$adultCertificationDate() != null ? realmGet$adultCertificationDate() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{lastNameCertification:");
        stringBuilder.append(realmGet$lastNameCertification() != null ? realmGet$lastNameCertification() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{emailCertification:");
        stringBuilder.append(realmGet$emailCertification() != null ? realmGet$emailCertification() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{isVip:");
        stringBuilder.append(realmGet$isVip() != null ? realmGet$isVip() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{isVipActive:");
        stringBuilder.append(realmGet$isVipActive() != null ? realmGet$isVipActive() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{vipCreateDate:");
        stringBuilder.append(realmGet$vipCreateDate() != null ? realmGet$vipCreateDate() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{profileOriginalImage:");
        stringBuilder.append(realmGet$profileOriginalImage() != null ? realmGet$profileOriginalImage() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{clientToken:");
        stringBuilder.append(realmGet$clientToken() != null ? realmGet$clientToken() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{onAir:");
        stringBuilder.append(realmGet$onAir());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{token:");
        stringBuilder.append(realmGet$token() != null ? realmGet$token() : "null");
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
        ModelUserRealmProxy aModelUser = (ModelUserRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aModelUser.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aModelUser.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aModelUser.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
