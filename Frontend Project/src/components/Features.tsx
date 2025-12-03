import { Card, CardContent } from "@/components/ui/card";
import { Users, Target, BarChart3, Calendar, Shield, Download, Database, TrendingUp, Award, ClipboardList, Video, Lock, Sparkles } from "lucide-react";
import { motion } from "framer-motion";

const features = [
  {
    icon: Lock,
    title: "Inicio de Sesión Institucional",
    description: "Accede de forma segura con tu cuenta institucional",
    color: "from-[#5453E0] to-[#7B7AFF]"
  },
  {
    icon: ClipboardList,
    title: "Rutinas Personalizadas",
    description: "Crea y edita tus rutinas con ejercicios predefinidos o personalizados",
    color: "from-[#7B7AFF] to-[#5453E0]"
  },
  {
    icon: Database,
    title: "Base de Datos de Ejercicios",
    description: "Acceso completo a ejercicios con descripción, videos y niveles de dificultad",
    color: "from-[#5453E0] to-[#9B9AFF]"
  },
  {
    icon: TrendingUp,
    title: "Registro de Progreso",
    description: "Registra tu progreso diario y semanal: repeticiones, tiempo y esfuerzo",
    color: "from-[#9B9AFF] to-[#5453E0]"
  },
  {
    icon: Users,
    title: "Supervisión de Entrenadores",
    description: "Los entrenadores visualizan tu progreso y rutinas en tiempo real",
    color: "from-[#5453E0] to-[#7B7AFF]"
  },
  {
    icon: Target,
    title: "Recomendaciones Personalizadas",
    description: "Recibe sugerencias de entrenadores según tu avance",
    color: "from-[#7B7AFF] to-[#9B9AFF]"
  },
  {
    icon: Award,
    title: "Rutinas Prediseñadas",
    description: "Accede a rutinas profesionales creadas por entrenadores certificados",
    color: "from-[#5453E0] to-[#7B7AFF]"
  },
  {
    icon: Calendar,
    title: "Eventos y Espacios",
    description: "Consulta horarios del gimnasio, clases de yoga y torneos disponibles",
    color: "from-[#9B9AFF] to-[#5453E0]"
  },
  {
    icon: Shield,
    title: "Panel Administrativo",
    description: "Gestión completa de entrenadores, usuarios y base de datos",
    color: "from-[#5453E0] to-[#7B7AFF]"
  },
  {
    icon: BarChart3,
    title: "Historial Completo",
    description: "Revisa todas tus actividades, rutinas pasadas y métricas de rendimiento",
    color: "from-[#7B7AFF] to-[#9B9AFF]"
  },
  {
    icon: Video,
    title: "Estadísticas Visuales",
    description: "Gráficos de progreso semanal y mensual para seguimiento visual",
    color: "from-[#5453E0] to-[#7B7AFF]"
  },
  {
    icon: Download,
    title: "Reportes en PDF",
    description: "Descarga tus reportes personales de progreso en formato PDF",
    color: "from-[#9B9AFF] to-[#5453E0]"
  }
];

const containerVariants = {
  hidden: { opacity: 0 },
  visible: {
    opacity: 1,
    transition: {
      staggerChildren: 0.1
    }
  }
};

const itemVariants = {
  hidden: { opacity: 0, y: 50, scale: 0.9 },
  visible: {
    opacity: 1,
    y: 0,
    scale: 1,
    transition: {
      duration: 0.6,
      ease: "easeOut"
    }
  }
};

export default function Features() {
  return (
    <section className="relative py-32 bg-gradient-to-b from-background via-[#5453E0]/5 to-background overflow-hidden">
      {/* Animated background with purple */}
      <div className="absolute inset-0">
        <div className="absolute top-0 left-1/4 w-[600px] h-[600px] bg-[#5453E0]/20 rounded-full blur-[150px] animate-pulse-slow" />
        <div className="absolute bottom-0 right-1/4 w-[500px] h-[500px] bg-[#7B7AFF]/15 rounded-full blur-[120px] animate-pulse-slow" style={{ animationDelay: '2s' }} />
        <div className="absolute inset-0 bg-[linear-gradient(to_right,#5453E0_0.3px,transparent_0.3px),linear-gradient(to_bottom,#5453E0_0.3px,transparent_0.3px)] bg-[size:50px_50px] opacity-10" />
      </div>
      
      <div className="relative z-10 max-w-7xl mx-auto px-6">
        <motion.div 
          className="text-center mb-20"
          initial={{ opacity: 0, y: 30 }}
          whileInView={{ opacity: 1, y: 0 }}
          viewport={{ once: true }}
          transition={{ duration: 0.8 }}
        >
          <motion.div 
            className="inline-flex items-center gap-2 px-6 py-3 rounded-full border-2 border-[#5453E0]/30 bg-gradient-to-r from-[#5453E0]/10 to-[#5453E0]/5 backdrop-blur-md mb-8"
            whileHover={{ scale: 1.05 }}
          >
            <Sparkles className="w-5 h-5 text-[#5453E0]" />
            <span className="font-['Montserrat'] font-bold text-sm text-[#5453E0] tracking-wide">12 FUNCIONES PREMIUM</span>
          </motion.div>
          <h2 className="font-['Montserrat'] font-black text-5xl md:text-7xl lg:text-8xl mb-8 tracking-tighter">
            <span className="text-transparent bg-clip-text bg-gradient-to-r from-[#5453E0] via-[#7B7AFF] to-[#5453E0] animate-gradient">
              FUNCIONALIDAD
            </span>
            <br />
            <span className="text-foreground">COMPLETA</span>
          </h2>
          <p className="font-['Montserrat'] text-xl md:text-2xl text-muted-foreground max-w-3xl mx-auto font-semibold">
            Todo lo que necesitas para alcanzar tus objetivos de fitness en un solo lugar
          </p>
        </motion.div>

        <motion.div 
          className="grid md:grid-cols-2 lg:grid-cols-3 gap-8"
          variants={containerVariants}
          initial="hidden"
          whileInView="visible"
          viewport={{ once: true, margin: "-100px" }}
        >
          {features.map((feature, index) => (
            <motion.div
              key={index}
              variants={itemVariants}
              whileHover={{ y: -10, scale: 1.02 }}
            >
              <Card className="border-2 border-[#5453E0]/20 bg-background/80 backdrop-blur-sm hover:border-[#5453E0]/50 hover:shadow-2xl hover:shadow-[#5453E0]/20 transition-all duration-300 group relative overflow-hidden h-full">
                {/* Gradient overlay on hover */}
                <div className={`absolute inset-0 bg-gradient-to-br ${feature.color} opacity-0 group-hover:opacity-10 transition-opacity duration-300`} />
                
                <CardContent className="p-8 relative z-10">
                  <motion.div 
                    className={`w-16 h-16 rounded-2xl bg-gradient-to-br ${feature.color} flex items-center justify-center mb-6 shadow-lg shadow-[#5453E0]/30 group-hover:scale-110 group-hover:rotate-6 transition-all duration-300`}
                    whileHover={{ rotate: [0, -10, 10, 0] }}
                  >
                    <feature.icon className="w-8 h-8 text-white" />
                  </motion.div>
                  <h3 className="font-['Montserrat'] font-black text-xl mb-3 text-foreground group-hover:text-[#5453E0] transition-colors">
                    {feature.title}
                  </h3>
                  <p className="font-['Montserrat'] text-muted-foreground leading-relaxed">
                    {feature.description}
                  </p>
                  
                  {/* Animated underline */}
                  <motion.div 
                    className="mt-4 h-1 bg-gradient-to-r from-[#5453E0] to-[#7B7AFF] rounded-full"
                    initial={{ width: 0 }}
                    whileInView={{ width: "100%" }}
                    viewport={{ once: true }}
                    transition={{ duration: 0.6, delay: index * 0.1 }}
                  />
                </CardContent>
              </Card>
            </motion.div>
          ))}
        </motion.div>
      </div>
    </section>
  );
}
