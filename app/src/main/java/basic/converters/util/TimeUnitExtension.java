package basic.converters.util;

/**
 * Created by edmar on 1/31/15.
 */
public enum TimeUnitExtension {
    //TODO add Centuries and Milleniums
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
        public long convert(long millis) {
            return toWeeks(millis);
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
        public long convert(long millis) {
            return toMonths(millis);
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
        public long convert(long millis) {
            return toYears(millis);
        }
        public long getMillis() {
            return MILLIS_PER_YEAR;
        }
    };

    /* these numbers are based on google's converter */
    private static final long MILLIS_PER_WEEK = 7 * 24 * 60 * 60 * 1000L; // 604,800,000
    private static final long MILLIS_PER_MONTH = ( long ) (2.62974 * Math.pow(10,9)); /* 2,629,740,000 */
    private static final long MILLIS_PER_YEAR = ( long ) ( 365.25 * 24 * 60 * 60 * 1000L ); /* 31,557,600,000 */

    public long toWeeks(long millis) {
        long result = (long)Math.floor((double)millis/MILLIS_PER_WEEK);
        return result;
    }

    public long toMonths(long millis) {
        long result = (long)Math.floor((double)millis/MILLIS_PER_MONTH);
        return result;
    }

    public long toYears(long millis) {
        long result = (long)Math.floor((double)millis/MILLIS_PER_YEAR);
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
