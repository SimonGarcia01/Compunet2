import { SUPABASE_URL, SUPABASE_PUBLISHABLE_KEY, supabaseConfig } from '@/config/supabase';

// Usar individualmente
console.log(SUPABASE_URL);
console.log(SUPABASE_PUBLISHABLE_KEY);

// O usar el objeto
console.log(supabaseConfig.url);
console.log(supabaseConfig.publishableKey);
