export type GoalType = 'cutting' | 'bulking' | 'performance' | 'lowgi' | 'recovery';
export type ContextType = 'none' | 'post-workout' | 'late-night' | 'office-lunch';

export interface GoalData {
  score: number;
  explanation: string;
}

export interface Dish {
  id: string;
  name: string;
  description: string;
  price: number;
  calories: number;
  protein: number;
  carbs: number;
  fat: number;
  fiber?: number;
  isFried?: boolean;
  isSugary?: boolean;
  isVeg: boolean;
  imageGradient: string;
  goals: Record<GoalType, GoalData>;
}

export const contexts: { id: ContextType; label: string; icon: string; color: string }[] = [
  { id: 'post-workout', label: 'Post-workout', icon: '🏋️', color: '#22c55e' },
  { id: 'late-night', label: 'Late night', icon: '🌙', color: '#6366f1' },
  { id: 'office-lunch', label: 'Office lunch', icon: '💼', color: '#f59e0b' },
];

export const goals: { id: GoalType; label: string; icon: string; color: string }[] = [
  { id: 'cutting', label: 'Cutting', icon: '🔥', color: '#ef4444' },
  { id: 'bulking', label: 'Bulking', icon: '💪', color: '#f97316' },
  { id: 'performance', label: 'Performance', icon: '🏃', color: '#22c55e' },
  { id: 'lowgi', label: 'Low-GI', icon: '🧠', color: '#3b82f6' },
  { id: 'recovery', label: 'Recovery', icon: '🩺', color: '#a855f7' },
];

export const dishes: Dish[] = [
  {
    id: '1',
    name: 'Grilled Chicken Salad',
    description: 'Tender grilled chicken breast on a bed of mixed greens with cherry tomatoes, cucumber, and light vinaigrette',
    price: 349,
    calories: 320,
    protein: 42,
    carbs: 12,
    fat: 14,
    fiber: 6,
    isFried: false,
    isSugary: false,
    isVeg: false,
    imageGradient: 'linear-gradient(135deg, #22c55e 0%, #15803d 100%)',
    goals: {
      cutting: {
        score: 92,
        explanation: 'High protein-to-calorie ratio supports muscle retention during deficit. Low carbs help manage hunger hormones.',
      },
      bulking: {
        score: 58,
        explanation: 'Solid protein source but may need additional carbs or sides to meet surplus goals.',
      },
      performance: {
        score: 65,
        explanation: 'Good protein but limited carbs for glycogen. Consider adding a carb source pre-workout.',
      },
      lowgi: {
        score: 88,
        explanation: 'Minimal carbs and those present are fiber-rich. Steady energy without blood sugar spikes.',
      },
      recovery: {
        score: 85,
        explanation: 'Excellent protein content aids muscle repair. Light and easy to digest post-activity.',
      },
    },
  },
  {
    id: '2',
    name: 'Butter Chicken with Naan',
    description: 'Creamy tomato-based curry with tender chicken pieces, served with fresh butter naan bread',
    price: 429,
    calories: 780,
    protein: 38,
    carbs: 62,
    fat: 42,
    fiber: 3,
    isFried: false,
    isSugary: false,
    isVeg: false,
    imageGradient: 'linear-gradient(135deg, #f97316 0%, #c2410c 100%)',
    goals: {
      cutting: {
        score: 35,
        explanation: 'Calorie-dense with significant fat content. Consider as an occasional treat or share the naan.',
      },
      bulking: {
        score: 88,
        explanation: 'Excellent calorie density with balanced macros. The combination supports both energy and muscle synthesis.',
      },
      performance: {
        score: 75,
        explanation: 'Good carb-protein combo for glycogen and recovery. Time it well around training sessions.',
      },
      lowgi: {
        score: 42,
        explanation: 'Naan is high-GI refined flour. The fat slows absorption somewhat, but still causes notable glucose rise.',
      },
      recovery: {
        score: 72,
        explanation: 'Decent protein and the carbs help replenish glycogen. Rich meal best suited for post-activity.',
      },
    },
  },
  {
    id: '3',
    name: 'Paneer Tikka',
    description: 'Marinated cottage cheese cubes grilled in tandoor with bell peppers and onions',
    price: 299,
    calories: 380,
    protein: 22,
    carbs: 8,
    fat: 28,
    fiber: 2,
    isFried: false,
    isSugary: false,
    isVeg: true,
    imageGradient: 'linear-gradient(135deg, #eab308 0%, #a16207 100%)',
    goals: {
      cutting: {
        score: 68,
        explanation: 'Moderate calories with decent protein. The fat content is filling but watch portion sizes.',
      },
      bulking: {
        score: 72,
        explanation: 'Good fat and protein profile. Pair with rice or roti to maximize caloric intake.',
      },
      performance: {
        score: 55,
        explanation: 'Limited carbs for fuel. Works better as a protein source alongside carb-rich sides.',
      },
      lowgi: {
        score: 82,
        explanation: 'Very low carb content. Fat and protein combination provides sustained energy release.',
      },
      recovery: {
        score: 70,
        explanation: 'Solid protein from dairy aids repair. Consider adding carbs for complete recovery nutrition.',
      },
    },
  },
  {
    id: '4',
    name: 'Dal Makhani with Rice',
    description: 'Slow-cooked black lentils in creamy tomato gravy served with steamed basmati rice',
    price: 279,
    calories: 520,
    protein: 18,
    carbs: 72,
    fat: 18,
    fiber: 12,
    isFried: false,
    isSugary: false,
    isVeg: true,
    imageGradient: 'linear-gradient(135deg, #78350f 0%, #451a03 100%)',
    goals: {
      cutting: {
        score: 45,
        explanation: 'High carb content adds calories quickly. The fiber helps with satiety but portion control is key.',
      },
      bulking: {
        score: 78,
        explanation: 'Great carb-to-protein ratio for energy surplus. Plant-based protein supports growth alongside other meals.',
      },
      performance: {
        score: 82,
        explanation: 'Excellent slow-release carbs for sustained energy. Ideal pre-workout meal timing 2-3 hours before.',
      },
      lowgi: {
        score: 55,
        explanation: 'Lentils are low-GI but white rice spikes glucose. Consider brown rice or smaller rice portion.',
      },
      recovery: {
        score: 68,
        explanation: 'Good carb replenishment but moderate protein. Consider adding a protein source for optimal recovery.',
      },
    },
  },
  {
    id: '5',
    name: 'Tandoori Prawns',
    description: 'Jumbo prawns marinated in yogurt and spices, char-grilled to perfection',
    price: 549,
    calories: 240,
    protein: 36,
    carbs: 4,
    fat: 8,
    fiber: 0,
    isFried: false,
    isSugary: false,
    isVeg: false,
    imageGradient: 'linear-gradient(135deg, #dc2626 0%, #991b1b 100%)',
    goals: {
      cutting: {
        score: 95,
        explanation: 'Exceptional protein density with minimal calories. One of the best cutting-friendly protein sources.',
      },
      bulking: {
        score: 52,
        explanation: 'Very lean — great protein but needs calorie-dense sides to contribute to surplus.',
      },
      performance: {
        score: 60,
        explanation: 'Excellent protein but almost no carbs. Pair with rice or bread for workout fuel.',
      },
      lowgi: {
        score: 94,
        explanation: 'Virtually zero carbs means no glycemic impact. Pure protein and healthy fats.',
      },
      recovery: {
        score: 88,
        explanation: 'High-quality complete protein ideal for muscle repair. Light and quick to digest.',
      },
    },
  },
  {
    id: '6',
    name: 'Chole Bhature',
    description: 'Spiced chickpea curry served with deep-fried fluffy bread',
    price: 199,
    calories: 720,
    protein: 16,
    carbs: 85,
    fat: 35,
    fiber: 8,
    isFried: true,
    isSugary: false,
    isVeg: true,
    imageGradient: 'linear-gradient(135deg, #ca8a04 0%, #713f12 100%)',
    goals: {
      cutting: {
        score: 25,
        explanation: 'Very calorie-dense with fried bread. Occasional indulgence — not aligned with deficit goals.',
      },
      bulking: {
        score: 85,
        explanation: 'Excellent calorie density for surplus. The combination provides sustained energy for intense training.',
      },
      performance: {
        score: 70,
        explanation: 'High carbs provide energy, but the heavy fat may slow digestion. Best not immediately pre-workout.',
      },
      lowgi: {
        score: 28,
        explanation: 'Fried refined flour causes rapid glucose spike. Chickpeas alone would be better for stable energy.',
      },
      recovery: {
        score: 55,
        explanation: 'Good carb replenishment but heavy. Moderate protein — may feel sluggish if eaten immediately post-workout.',
      },
    },
  },
  {
    id: '7',
    name: 'Egg Biryani',
    description: 'Fragrant basmati rice layered with spiced boiled eggs, caramelized onions, and aromatic herbs',
    price: 259,
    calories: 480,
    protein: 18,
    carbs: 58,
    fat: 20,
    fiber: 2,
    isFried: false,
    isSugary: false,
    isVeg: false,
    imageGradient: 'linear-gradient(135deg, #fbbf24 0%, #b45309 100%)',
    goals: {
      cutting: {
        score: 48,
        explanation: 'Moderate calories but carb-heavy. Reasonable as a main meal if fitting daily targets.',
      },
      bulking: {
        score: 80,
        explanation: 'Well-balanced macros with good calorie content. Rice provides sustained energy for training.',
      },
      performance: {
        score: 85,
        explanation: 'Excellent balance of carbs and protein. Great meal 2-3 hours before activity for sustained energy.',
      },
      lowgi: {
        score: 50,
        explanation: 'Basmati is medium-GI. The eggs and fat slow glucose absorption somewhat.',
      },
      recovery: {
        score: 75,
        explanation: 'Good carb-protein combo for glycogen and muscle repair. Well-rounded recovery meal.',
      },
    },
  },
  {
    id: '8',
    name: 'Greek Yogurt Parfait',
    description: 'Thick Greek yogurt layered with mixed berries, honey drizzle, and crunchy granola',
    price: 229,
    calories: 340,
    protein: 24,
    carbs: 38,
    fat: 12,
    fiber: 4,
    isFried: false,
    isSugary: true,
    isVeg: true,
    imageGradient: 'linear-gradient(135deg, #ec4899 0%, #be185d 100%)',
    goals: {
      cutting: {
        score: 72,
        explanation: 'Good protein with moderate calories. Watch the granola portion — it adds up quickly.',
      },
      bulking: {
        score: 65,
        explanation: 'Decent snack but may need larger portions or additional meals to meet surplus.',
      },
      performance: {
        score: 78,
        explanation: 'Quick-digesting carbs with protein make this ideal for post-workout or light pre-workout fuel.',
      },
      lowgi: {
        score: 58,
        explanation: 'Honey and granola add sugar. The protein and fat buffer it, but berries alone would be better.',
      },
      recovery: {
        score: 82,
        explanation: 'Excellent recovery snack — protein for repair, carbs for glycogen, and antioxidants from berries.',
      },
    },
  },
];

