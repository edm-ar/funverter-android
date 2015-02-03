package basic.converters.utilities;

import java.util.concurrent.TimeUnit;

/**
 * Created by edmar on 1/31/15.
 */
public enum TimeUnitExtension {
    WEEKS {
        public long toMonths(long d, TimeUnitExtension s) {
            return s.toMonths(d * s.getMillis());
        }
        public long toYears(long d, TimeUnitExtension s) {
            return s.toYears(d * s.getMillis());
        }
        public long convert(long d, TimeUnitExtension s) {
            return s.toWeeks(d * s.getMillis());
        }
        public long getMillis() {
            return MILLIS_PER_WEEK;
        }
    },
    MONTHS {
        public long toWeeks(long d, TimeUnitExtension s) {
            return s.toWeeks(d * s.getMillis());
        }
        public long toYears(long d, TimeUnitExtension s) {
            return s.toYears(d * s.getMillis());
        }
        public long convert(long d, TimeUnitExtension s) {
            return s.toMonths(d * s.getMillis());
        }
        public long getMillis() {
            return MILLIS_PER_MONTH;
        }
    },
    YEARS {
        public long toWeeks(long d, TimeUnitExtension s) {
            return s.toWeeks(d * s.getMillis());
        }
        public long toMonths(long d, TimeUnitExtension s) {
            return s.toMonths(d * s.getMillis());
        }
        public long convert(long d, TimeUnitExtension s) {
            return s.toYears(d * s.getMillis());
        }
        public long getMillis() {
            return MILLIS_PER_YEAR;
        }
    };

    private static final long MILLIS_PER_WEEK = 7 * 24 * 60 * 60 * 1000L; // 604,800,000
    private static final long MILLIS_PER_MONTH = ( long ) ( 30.4375 * 24 * 60 * 60 * 1000L ); /* 2,629,800,000 */
    private static final long MILLIS_PER_YEAR = ( long ) ( 365.25d * 24 * 60 * 60 * 1000L ); /* 31,557,600,000 */

    public long toWeeks(long millis) {
        long result = (long)Math.ceil((double)millis/MILLIS_PER_WEEK);
        return result;
    }

    public long toMonths(long millis) {
        long result = (long)Math.ceil((double)millis/MILLIS_PER_MONTH);
        return result;
    }

    public long toYears(long millis) {
        long result = (long)Math.ceil((double)millis/MILLIS_PER_YEAR);
        return result;
    }

    public long convert(long sourceDuration, TimeUnitExtension sourceUnit) {
        throw new AbstractMethodError();
    }

    public long convert(long sourceDuration) {
        throw new AbstractMethodError();
    }

    public long getMillis() {
        throw new AbstractMethodError();
    }
}
